package com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUsuarioDto {

    private String username;
    private String telefono;
    private String nombre;
    private String apellidos;
    private String email;
    private String visualizacion;
    private String password;
    private String password2;

}
