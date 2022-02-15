package com.salesianostriana.dam.DanielOlivaMiarma.usuarios.controller;

import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto.CreateUsuarioDto;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto.GetUsuarioDto;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto.UsuarioDtoConverter;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.TipoVisualizacion;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.Usuario;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.services.impl.UsuarioImplService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "User Controller")
public class UsuarioController {

    private final UsuarioImplService usuarioImplService;
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
        Usuario nuevo = usuarioImplService.save(nuevoPropietario);

        if (nuevo == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(usuarioDtoConverter.usuarioEntityToGetUsuarioDto(nuevo));

    }

    @Operation(summary = "Muestra el perfil de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el perfil",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado el perfil",
                    content = @Content),
    })
    @GetMapping("profile/{id}")
    public ResponseEntity<Usuario> findOneProfile (@PathVariable Long id) {

        if (usuarioImplService.findById(id).isEmpty() || usuarioImplService.findById(id).get().getTipoVisualizacion().equals(TipoVisualizacion.PRIVADO)) {

            return ResponseEntity.notFound().build();

        } else {

            return ResponseEntity.ok().body(usuarioImplService.findById(id).get());

        }

    }

    @Operation(summary = "Muestra el perfil de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el perfil",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado el perfil",
                    content = @Content),
    })
    @GetMapping("profile/me")
    public ResponseEntity<Usuario> findMyProfile (@AuthenticationPrincipal Usuario usuarioAuth) {

        if (usuarioImplService.findById(usuarioAuth.getId()).isEmpty()) {

            return ResponseEntity.notFound().build();

        } else {

            return ResponseEntity.ok().body(usuarioImplService.findById(usuarioAuth.getId()).get());

        }

    }

}
