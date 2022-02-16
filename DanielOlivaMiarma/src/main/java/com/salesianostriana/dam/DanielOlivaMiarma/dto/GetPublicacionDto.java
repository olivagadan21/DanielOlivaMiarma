package com.salesianostriana.dam.DanielOlivaMiarma.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPublicacionDto {

    private String titulo;
    private String texto;
    private String tipoPublicacion;
    private String fichero;

}
