package com.salesianostriana.dam.DanielOlivaMiarma.usuarios.controller;

import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto.CreateUsuarioDto;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto.GetUsuarioDto;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.dto.UsuarioDtoConverter;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.TipoVisualizacion;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "User Controller")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioDtoConverter usuarioDtoConverter;

    @Operation(summary = "Crea un nuevo usuario publico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha creado el nuevo usuario publico",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetUsuarioDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha creado el nuevo usuario publico",
                    content = @Content),
    })
    @PostMapping("/auth/register/public")
    public ResponseEntity<GetUsuarioDto> newUserPublic(@RequestBody CreateUsuarioDto newUser) {

        if (newUser==null)
            return ResponseEntity.badRequest().build();
        else {
            Usuario nuevo = usuarioService.savePublic(newUser);
            return ResponseEntity.ok(usuarioDtoConverter.usuarioEntityToGetUsuarioDto(nuevo));
        }

    }

    @Operation(summary = "Crea un nuevo usuario privado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha creado el nuevo usuario privado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetUsuarioDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha creado el nuevo usuario privado",
                    content = @Content),
    })
    @PostMapping("/auth/register/private")
    public ResponseEntity<GetUsuarioDto> newUserPrivate(@RequestBody CreateUsuarioDto newUser) {

        if (newUser==null)
            return ResponseEntity.badRequest().build();
        else {
            Usuario nuevo = usuarioService.savePrivate(newUser);
            return ResponseEntity.ok(usuarioDtoConverter.usuarioEntityToGetUsuarioDto(nuevo));
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
    @GetMapping("profile/{id}")
    public ResponseEntity<Usuario> findOneProfile (@PathVariable Long id) {

        if (usuarioService.findById(id).isEmpty() || usuarioService.findById(id).get().getTipoVisualizacion().equals(TipoVisualizacion.PRIVADO)) {

            return ResponseEntity.notFound().build();

        } else {

            return ResponseEntity.ok().body(usuarioService.findById(id).get());

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

        if (usuarioService.findById(usuarioAuth.getId()).isEmpty()) {

            return ResponseEntity.notFound().build();

        } else {

            return ResponseEntity.ok().body(usuarioService.findById(usuarioAuth.getId()).get());

        }

    }

}
