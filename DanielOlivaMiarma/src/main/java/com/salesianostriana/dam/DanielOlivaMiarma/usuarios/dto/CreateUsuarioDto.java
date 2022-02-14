package com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto;

import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.RolUsuario;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.TipoVisualizacion;
import lombok.*;

import java.time.LocalDate;

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
    private LocalDate fechaNacimiento;
    private String email;
    private RolUsuario rolUsuario;
    private TipoVisualizacion tipoVisualizacion;
    private String password;
    private String password2;

}
