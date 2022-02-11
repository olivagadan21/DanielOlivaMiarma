package com.salesianostriana.dam.DanielOlivaMiarma.usuarios.controller;

import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto.CreateUsuarioDto;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto.GetUsuarioDto;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto.UsuarioDtoConverter;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.Usuario;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "User Controller")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioDtoConverter usuarioDtoConverter;

    @Operation(summary = "Crea un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha creado el nuevo usuario",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetUsuarioDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha creado el nuevo usuario",
                    content = @Content),
    })
    @PostMapping("/auth/register")
    public ResponseEntity<GetUsuarioDto> nuevoPropietario(@RequestBody CreateUsuarioDto nuevoPropietario) {
        Usuario nuevo = usuarioService.save(nuevoPropietario);

        if (nuevo == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(usuarioDtoConverter.usuarioEntityToGetUsuarioDto(nuevo));

    }

}
