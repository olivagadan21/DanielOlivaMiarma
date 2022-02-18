package com.salesianostriana.dam.DanielOlivaMiarma.services.impl;

import com.salesianostriana.dam.DanielOlivaMiarma.dto.CreatePublicacionDto;
import com.salesianostriana.dam.DanielOlivaMiarma.model.Publicacion;
import com.salesianostriana.dam.DanielOlivaMiarma.model.TipoPublicacion;
import com.salesianostriana.dam.DanielOlivaMiarma.repos.PublicacionRepository;
import com.salesianostriana.dam.DanielOlivaMiarma.services.PublicacionService;
import com.salesianostriana.dam.DanielOlivaMiarma.services.StorageService;
import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublicacionImplService implements PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final StorageService storageService;

    public List<Publicacion> findAllPublic() {

        return publicacionRepository.findAllPublic();

    }

    public List<Publicacion> findAllByUserNick(String nick) {

        return publicacionRepository.findAllByUserNick(nick);

    }

    public List<Publicacion> findAll() {
        return publicacionRepository.findAll();
    }

    public Optional<Publicacion> findById(Long id) {
        return publicacionRepository.findById(id);
    }

    public Publicacion save(CreatePublicacionDto p, MultipartFile file) {

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        Publicacion publicacion = Publicacion.builder()
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .tipoPublicacion(TipoPublicacion.valueOf(p.getTipoPublicacion()))
                .fichero(filename)
                .build();


        return publicacionRepository.save(publicacion);

    }

    public Publicacion edit(CreatePublicacionDto p, MultipartFile file, Long id) {

        Publicacion publicacion = publicacionRepository.findById(id).get();

        storageService.deleteFile(publicacion.getFichero());

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        publicacion.setTitulo(p.getTitulo());
        publicacion.setTexto(p.getTexto());
        publicacion.setFichero(filename);

        return publicacionRepository.save(publicacion);

    }

    @Override
    public void delete(Publicacion p) {
        publicacionRepository.delete(p);
    }

    @Override
    public void deleteById(Long id) {

        storageService.deleteFile(publicacionRepository.findById(id).get().getFichero());

        publicacionRepository.deleteById(id);

    }
}