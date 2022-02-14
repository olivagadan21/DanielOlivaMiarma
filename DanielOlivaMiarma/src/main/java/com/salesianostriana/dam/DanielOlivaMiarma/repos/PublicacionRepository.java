package com.salesianostriana.dam.DanielOlivaMiarma.repos;

import com.salesianostriana.dam.DanielOlivaMiarma.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PublicacionRepository extends JpaRepository<Publicacion, UUID>{

    @Query(value = """
            select *
            from Publicacion p
            where p.TipoPublicacion = "PUBLICA" 
            """, nativeQuery = true)
    List<Publicacion> findAllPublic();

}
