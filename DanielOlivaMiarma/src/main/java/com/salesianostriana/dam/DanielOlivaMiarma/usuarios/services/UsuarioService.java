package com.salesianostriana.dam.DanielOlivaMiarma.usuarios.services;

import com.salesianostriana.dam.DanielOlivaMiarma.model.Publicacion;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> findAll();

    Optional<Usuario> findById(Long id);

    Usuario save(Usuario u);

    void delete(Usuario u);

    void deleteById(Long id);

}
