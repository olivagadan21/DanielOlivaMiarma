package com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUsuarioDto {

    private String username;
    private String avatar;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private String password2;

}
