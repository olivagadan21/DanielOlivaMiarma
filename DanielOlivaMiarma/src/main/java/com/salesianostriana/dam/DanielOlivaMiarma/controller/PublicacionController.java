package com.salesianostriana.dam.DanielOlivaMiarma.controller;

import com.salesianostriana.dam.DanielOlivaMiarma.model.Publicacion;
import com.salesianostriana.dam.DanielOlivaMiarma.model.TipoPublicacion;
import com.salesianostriana.dam.DanielOlivaMiarma.services.PublicacionService;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.Usuario;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Operation(summary = "Edita una publicacion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado la publicación",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publicacion.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha editado la publicación",
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

    @Operation(summary = "Elimina una publicacion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha eliminado la publicación",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publicacion.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha eliminado la publicación",
                    content = @Content),
    })
    @DeleteMapping("{id}")
    public ResponseEntity deletePost(@PathVariable UUID id) {

        if (publicacionService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {

            publicacionService.deleteById(id);

            return ResponseEntity.noContent().build();

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

    @Operation(summary = "Obtiene una publicación si es pública")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la publicación",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publicacion.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado la publicación",
                    content = @Content),
    })
    @GetMapping("{id}")
    public ResponseEntity<Publicacion> findOnePost (@PathVariable UUID id) {

        if (publicacionService.findById(id).isEmpty() || publicacionService.findById(id).get().getTipoPublicacion().equals(TipoPublicacion.PRIVADA)) {

            return ResponseEntity.notFound().build();

        } else {

            return ResponseEntity.ok().body(publicacionService.findById(id).get());

        }

    }

    @Operation(summary = "Obtiene las publicaciones de una persona")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las publicaciones",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publicacion.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han encontrado las publicaciones",
                    content = @Content),
    })
    @GetMapping("{nick}")
    public ResponseEntity<List<Publicacion>> findPostsByNick (@PathVariable String nick) {

        if (nick.isEmpty() || usuarioService.loadUserByUsername(nick)==null || publicacionService.findAllByUserNick(nick).isEmpty()) {

            return ResponseEntity.notFound().build();

        } else {

            return ResponseEntity.ok().body(publicacionService.findAllByUserNick(nick));

        }

    }

    @Operation(summary = "Obtiene mis publicaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado mis publicaciones",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Publicacion.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han encontrado mis publicaciones",
                    content = @Content),
    })
    @GetMapping("me")
    public ResponseEntity<List<Publicacion>> findAllMyPosts (@AuthenticationPrincipal Usuario usuarioAuth) {

        Optional<Usuario> usuario = usuarioService.loadUserById(usuarioAuth.getId());

        if (usuario.isEmpty()) {

            return ResponseEntity.notFound().build();

        } else {

            return ResponseEntity.ok().body(usuario.get().getPublicacionList());

        }

    }

}