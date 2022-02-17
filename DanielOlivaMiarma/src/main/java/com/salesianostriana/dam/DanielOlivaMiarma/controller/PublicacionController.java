package com.salesianostriana.dam.DanielOlivaMiarma.controller;

import com.salesianostriana.dam.DanielOlivaMiarma.dto.CreatePublicacionDto;
import com.salesianostriana.dam.DanielOlivaMiarma.model.Publicacion;
import com.salesianostriana.dam.DanielOlivaMiarma.model.TipoPublicacion;
import com.salesianostriana.dam.DanielOlivaMiarma.services.impl.PublicacionImplService;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.Usuario;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.services.impl.UsuarioImplService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Publicacion", description = "Controller de las publicaciones")
@RequestMapping("/post/")
public class PublicacionController {

    private final PublicacionImplService publicacionImplService;
    private final UsuarioImplService usuarioImplService;

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
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addPost (@RequestPart("file") MultipartFile file,
                                      @RequestPart("post") CreatePublicacionDto newPost) {

        if (newPost.getTitulo().isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(publicacionImplService.save(newPost, file));

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
    public ResponseEntity<Publicacion> editPost (@RequestPart("file") MultipartFile file,
                                                 @RequestPart("post") CreatePublicacionDto publicacion,
                                                 @PathVariable Long id) {

        if (publicacion == null || id == null) {
            return ResponseEntity.notFound().build();
        } else {

            return ResponseEntity.of(
                    publicacionImplService.findById(id).map(p ->{
                        p.setTexto(publicacion.getTexto());
                        p.setTitulo(publicacion.getTitulo());
                        p.setTipoPublicacion(TipoPublicacion.valueOf(publicacion.getTipoPublicacion()));
                        p.setFichero(p.getFichero());
                        publicacionImplService.save(publicacion, file);

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
    public ResponseEntity deletePost(@PathVariable Long id) {

        if (publicacionImplService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {

            publicacionImplService.deleteById(id);

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

        List<Publicacion> publicaciones = publicacionImplService.findAllPublic();

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
    public ResponseEntity<Publicacion> findOnePost (@PathVariable Long id) {

        if (publicacionImplService.findById(id).isEmpty() || publicacionImplService.findById(id).get().getTipoPublicacion().equals(TipoPublicacion.PRIVADA)) {

            return ResponseEntity.notFound().build();

        } else {

            return ResponseEntity.ok().body(publicacionImplService.findById(id).get());

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
    @GetMapping("user/{nick}")
    public ResponseEntity<List<Publicacion>> findPostsByNick (@PathVariable String nick) {

        if (nick.isEmpty() || usuarioImplService.loadUserByUsername(nick)==null || publicacionImplService.findAllByUserNick(nick).isEmpty()) {

            return ResponseEntity.notFound().build();

        } else {

            return ResponseEntity.ok().body(publicacionImplService.findAllByUserNick(nick));

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

        Optional<Usuario> usuario = usuarioImplService.findById(usuarioAuth.getId());

        return usuario.map(value -> ResponseEntity.ok().body(value.getPublicacionList())).orElseGet(() -> ResponseEntity.notFound().build());

    }

}