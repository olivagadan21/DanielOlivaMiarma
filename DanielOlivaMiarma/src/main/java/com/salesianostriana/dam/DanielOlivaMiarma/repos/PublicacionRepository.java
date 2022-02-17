package com.salesianostriana.dam.DanielOlivaMiarma.repos;

import com.salesianostriana.dam.DanielOlivaMiarma.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long>{

    @Query(value = """
            select *
            from Publicacion p
            where p.tipo_publicacion = "PUBLICA"
            """, nativeQuery = true)
    List<Publicacion> findAllPublic();

    @Query(value = """
            select *
            from Publicacion p
            where p.usuario.username = :nick
            """, nativeQuery = true)
    List<Publicacion> findAllByUserNick(String nick);

}