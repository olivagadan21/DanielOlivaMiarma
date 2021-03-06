package com.salesianostriana.dam.DanielOlivaMiarma.usuarios.repos;

import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.RolUsuario;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findFirstByEmail(String email);
    Optional<Usuario> findById(UUID id);
    Page<Usuario> findByRol (RolUsuario rol, Pageable pageable);
    boolean existsByName(String name);

}