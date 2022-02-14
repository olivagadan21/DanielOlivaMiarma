package com.salesianostriana.dam.DanielOlivaMiarma.controller;

import com.salesianostriana.dam.DanielOlivaMiarma.model.Publicacion;
import com.salesianostriana.dam.DanielOlivaMiarma.model.TipoPublicacion;
import com.salesianostriana.dam.DanielOlivaMiarma.services.PublicacionService;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Publicacion", description = "Controller de las publicaciones")
@RequestMapping("/post/")
public class PublicacionController {

    private final PublicacionService publicacionService;
    private final UsuarioService usuarioService;

    @Operation(summary = "Crea una nueva publicacion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha creado la nueva publicación",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publicacion.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha creado la nueva publicación",
                    content = @Content),
    })
    @PostMapping("")
    public ResponseEntity<Publicacion> addPost(@RequestBody Publicacion publicacion) {

        if (publicacion.getTitulo().isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {

            publicacionService.save(publicacion);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(publicacion);
        }
    }

    @Operation(summary = "Crea una nueva publicacion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha creado la nueva publicación",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publicacion.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha creado la nueva publicación",
                    content = @Content),
    })
    @PutMapping("{id}")
    public ResponseEntity<Publicacion> editPost(@RequestBody Publicacion publicacion, @PathVariable UUID id) {

        if (publicacion == null || id == null) {
            return ResponseEntity.notFound().build();
        } else {

            return ResponseEntity.of(
                    publicacionService.findById(id).map(p ->{
                        p.setTexto(publicacion.getTexto());
                        p.setTitulo(publicacion.getTitulo());
                        p.setTipoPublicacion(publicacion.getTipoPublicacion());
                        p.setFichero(p.getFichero());
                        publicacionService.save(p);

                        return p;
                    })
            );
        }
    }

    @Operation(summary = "Obtiene lista de publicaciones públicas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado lista de publicaciones públicas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publicacion.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han encontrado las publicaciones públicas",
                    content = @Content),
    })
    @GetMapping("public")
    public ResponseEntity<List<Publicacion>> findAllPublic () {

        List<Publicacion> publicaciones = publicacionService.findAllPublic();

        if (publicaciones.isEmpty()) {

            return ResponseEntity.notFound().build();

        } else {

            return ResponseEntity.ok().body(publicaciones);

        }

    }

}
