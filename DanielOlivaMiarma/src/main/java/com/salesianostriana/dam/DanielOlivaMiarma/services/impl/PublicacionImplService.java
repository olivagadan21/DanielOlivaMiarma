package com.salesianostriana.dam.DanielOlivaMiarma.services.impl;

import com.salesianostriana.dam.DanielOlivaMiarma.model.Publicacion;
import com.salesianostriana.dam.DanielOlivaMiarma.repos.PublicacionRepository;
import com.salesianostriana.dam.DanielOlivaMiarma.services.PublicacionService;
import com.salesianostriana.dam.DanielOlivaMiarma.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

        List<Publicacion> listPostsByNick = new ArrayList<>();

        for (Publicacion p:publicacionRepository.findAll()) {

            if (p.getUsuario().getUsername()==nick)
                listPostsByNick.add(p);

        }

        return listPostsByNick;

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
    public Publicacion save(Publicacion p, MultipartFile file) {

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        return publicacionRepository.save(p);
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