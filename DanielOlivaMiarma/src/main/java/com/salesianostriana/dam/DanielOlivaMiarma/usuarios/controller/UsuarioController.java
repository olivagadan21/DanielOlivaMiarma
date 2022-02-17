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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

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
    @PostMapping(value = "/auth/register/public", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GetUsuarioDto> newUserPublic(@RequestPart("user") CreateUsuarioDto newUser,
                                                       @RequestPart("file") MultipartFile file) {

        if (newUser==null)
            return ResponseEntity.badRequest().build();
        else {
            Usuario nuevo = usuarioService.savePublic(newUser, file);
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
    public ResponseEntity<GetUsuarioDto> newUserPrivate (@RequestPart("user") CreateUsuarioDto newUser,
                                                         @RequestPart("file") MultipartFile file) {

        if (newUser==null)
            return ResponseEntity.badRequest().build();
        else {
            Usuario nuevo = usuarioService.savePrivate(newUser, file);
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
    public ResponseEntity<Usuario> findOneProfile (@PathVariable UUID id) {

        if (usuarioService.findById(id).isEmpty() || usuarioService.findById(id).get().getTipoVisualizacion().equals(TipoVisualizacion.PRIVADO))
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok().body(usuarioService.findById(id).get());

    }

    @Operation(summary = "Editar mi perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado el perfil",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha editado el perfil",
                    content = @Content),
    })
    @PutMapping("profile/me")
    public ResponseEntity<Usuario> editMyProfile  (@RequestPart("user") CreateUsuarioDto newUser,
                                                   @RequestPart("file") MultipartFile file,
                                                   @AuthenticationPrincipal Usuario usuarioAuth) {

        if (usuarioAuth == null)
            return ResponseEntity.notFound().build();
        else{
            return ResponseEntity.ok().body(usuarioService.editMyProfile(newUser, file, usuarioAuth));
        }

    }

}
