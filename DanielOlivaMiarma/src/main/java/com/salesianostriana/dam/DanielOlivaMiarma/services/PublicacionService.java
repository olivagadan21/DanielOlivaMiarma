package com.salesianostriana.dam.DanielOlivaMiarma.services;

import com.salesianostriana.dam.DanielOlivaMiarma.dto.CreatePublicacionDto;
import com.salesianostriana.dam.DanielOlivaMiarma.model.Publicacion;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PublicacionService {

    List<Publicacion> findAll();

    Optional<Publicacion> findById(Long id);

    Publicacion save(CreatePublicacionDto p, MultipartFile mf);

    void delete(Publicacion p);

    void deleteById(Long id);

}
