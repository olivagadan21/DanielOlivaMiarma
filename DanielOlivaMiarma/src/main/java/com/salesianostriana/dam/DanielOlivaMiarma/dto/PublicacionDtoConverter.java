package com.salesianostriana.dam.DanielOlivaMiarma.dto;

import com.salesianostriana.dam.DanielOlivaMiarma.model.Publicacion;
import org.springframework.stereotype.Component;

@Component
public class PublicacionDtoConverter {

    public GetPublicacionDto publicacionToGetPublicacionDto (Publicacion publicacion) {

        return GetPublicacionDto.builder()
                .titulo(publicacion.getTitulo())
                .texto(publicacion.getTexto())
                .tipoPublicacion(publicacion.getTipoPublicacion().toString())
                .build();

    }

}
