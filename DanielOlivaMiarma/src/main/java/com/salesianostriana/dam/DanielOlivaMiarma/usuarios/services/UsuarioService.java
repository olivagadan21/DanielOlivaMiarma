package com.salesianostriana.dam.DanielOlivaMiarma.usuarios.services;

import com.salesianostriana.dam.DanielOlivaMiarma.services.base.BaseService;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto.CreateUsuarioDto;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.Usuario;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.repos.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UsuarioService extends BaseService<Usuario, UUID, UsuarioRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public Usuario save(CreateUsuarioDto newUser) {
        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {
            Usuario usuario = Usuario.builder()
                    .contraseña(passwordEncoder.encode(newUser.getPassword()))
                    .avatar(newUser.getAvatar())
                    .nombre(newUser.getNombre())
                    .apellidos(newUser.getApellidos())
                    .email(newUser.getEmail())
                    .build();
            return save(usuario);
        } else {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return this.repositorio.findFirstByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));

    }

    public Optional<Usuario> loadUserById(UUID id) throws UsernameNotFoundException {
        return this.repositorio.findById(id);
    }

}
