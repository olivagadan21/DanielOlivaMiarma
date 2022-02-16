package com.salesianostriana.dam.DanielOlivaMiarma.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePublicacionDto {

    private String titulo;
    private String texto;
    private String tipoPublicacion;

}
