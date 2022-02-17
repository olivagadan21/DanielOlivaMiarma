package com.salesianostriana.dam.DanielOlivaMiarma.usuarios.services.impl;

import com.salesianostriana.dam.DanielOlivaMiarma.services.StorageService;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto.CreateUsuarioDto;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.RolUsuario;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.TipoVisualizacion;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.Usuario;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.repos.UsuarioRepository;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UsuarioImplService extends UsuarioService<Usuario, UUID, UsuarioRepository> implements UserDetailsService {

    private final StorageService storageService;
    private final PasswordEncoder passwordEncoder;

    public Usuario savePublic(CreateUsuarioDto newUser, MultipartFile file) {
        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {
            Usuario usuario = Usuario.builder()
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .nombre(newUser.getNombre())
                    .apellidos(newUser.getApellidos())
                    .email(newUser.getEmail())
                    .rol(RolUsuario.USUARIO)
                    .tipoVisualizacion(TipoVisualizacion.PUBLICO)
                    .telefono(newUser.getTelefono())
                    .avatar(file.getOriginalFilename())
                    .build();

            String filename = storageService.store(file);

            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(filename)
                    .toUriString();

            return save(usuario);
        } else {
            return null;
        }
    }

    public Usuario savePrivate(CreateUsuarioDto newUser, MultipartFile file) {
        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {
            Usuario usuario = Usuario.builder()
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .nombre(newUser.getNombre())
                    .apellidos(newUser.getApellidos())
                    .email(newUser.getEmail())
                    .rol(RolUsuario.USUARIO)
                    .tipoVisualizacion(TipoVisualizacion.PRIVADO)
                    .telefono(newUser.getTelefono())
                    .avatar(file.getOriginalFilename())
                    .build();

            String filename = storageService.store(file);

            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(filename)
                    .toUriString();

            return save(usuario);
        } else {
            return null;
        }
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repositorio.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
    }

    public Page<Usuario> loadUserByRol(RolUsuario rol, Pageable pageable) throws UsernameNotFoundException {
        return repositorio.findByRol(rol, pageable);
    }

    public Optional<Usuario> loadUserById(UUID id) throws UsernameNotFoundException {
        return findById(id);
    }

    public Usuario editMyProfile  (CreateUsuarioDto newUser, MultipartFile file, Usuario usuarioAuth) {

        usuarioAuth.setNombre(newUser.getNombre());
        usuarioAuth.setApellidos(newUser.getApellidos());
        usuarioAuth.setEmail(newUser.getEmail());
        usuarioAuth.setTelefono(newUser.getTelefono());
        usuarioAuth.setPassword(newUser.getPassword());

        storageService.deleteFile(usuarioAuth.getAvatar());

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        save(usuarioAuth);

        return usuarioAuth;

    }

}