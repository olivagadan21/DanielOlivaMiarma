package com.salesianostriana.dam.DanielOlivaMiarma.usuarios.repos;

import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findFirstByUsername(String username);
    Optional<Usuario> findById(UUID id);

}