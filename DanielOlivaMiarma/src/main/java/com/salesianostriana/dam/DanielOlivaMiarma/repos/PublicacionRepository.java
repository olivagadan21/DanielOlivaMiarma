package com.salesianostriana.dam.DanielOlivaMiarma.repos;

import com.salesianostriana.dam.DanielOlivaMiarma.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PublicacionRepository extends JpaRepository<Publicacion, UUID>{

    List<Publicacion> findAllPublic();

}
