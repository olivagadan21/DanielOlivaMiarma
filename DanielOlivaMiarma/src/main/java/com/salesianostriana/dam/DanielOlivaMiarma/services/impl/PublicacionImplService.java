package com.salesianostriana.dam.DanielOlivaMiarma.services.impl;

import com.salesianostriana.dam.DanielOlivaMiarma.dto.CreatePublicacionDto;
import com.salesianostriana.dam.DanielOlivaMiarma.model.Publicacion;
import com.salesianostriana.dam.DanielOlivaMiarma.model.TipoPublicacion;
import com.salesianostriana.dam.DanielOlivaMiarma.repos.PublicacionRepository;
import com.salesianostriana.dam.DanielOlivaMiarma.services.PublicacionService;
import com.salesianostriana.dam.DanielOlivaMiarma.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublicacionImplService implements PublicacionService {

    private PublicacionRepository publicacionRepository;
    private StorageService storageService;

    public PublicacionImplService() {
    }

    public List<Publicacion> findAllPublic() {

        return publicacionRepository.findAllPublic();

    }

    public List<Publicacion> findAllByUserNick(String nick) {

        return publicacionRepository.findAllByUserNick(nick);

    }


    @Override
    public List<Publicacion> findAll() {
        return publicacionRepository.findAll();
    }

    @Override
    public Optional<Publicacion> findById(Long id) {
        return publicacionRepository.findById(id);
    }

    @Override
    public Publicacion save(CreatePublicacionDto p, MultipartFile file) {

        Publicacion publicacion = Publicacion.builder()
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .tipoPublicacion(TipoPublicacion.valueOf(p.getTipoPublicacion()))
                .build();

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        return publicacionRepository.save(publicacion);

    }

    @Override
    public void delete(Publicacion p) {
        publicacionRepository.delete(p);
    }

    @Override
    public void deleteById(Long id) {
        publicacionRepository.deleteById(id);
    }
}