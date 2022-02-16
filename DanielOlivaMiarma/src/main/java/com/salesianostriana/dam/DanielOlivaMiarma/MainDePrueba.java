package com.salesianostriana.dam.DanielOlivaMiarma;

import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto.CreateUsuarioDto;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MainDePrueba {

    private final UsuarioService usuarioService;

    @PostConstruct
    public void test() {

        CreateUsuarioDto usuario = CreateUsuarioDto.builder()
                .nombre("Daniel")
                .apellidos("Oliva García")
                .avatar("danieloliva.jpg")
                .email("danieloliva@gmail.com")
                .username("danieloliva")
                .password("12345678")
                .password2("12345678")
                .visualizacion("USUARIO")
                .telefono("615238959")
                .build();

        usuarioService.savePublic(usuario);

    }

}
