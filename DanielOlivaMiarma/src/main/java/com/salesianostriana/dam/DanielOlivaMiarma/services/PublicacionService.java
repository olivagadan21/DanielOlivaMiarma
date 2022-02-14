package com.salesianostriana.dam.DanielOlivaMiarma.services;

import com.salesianostriana.dam.DanielOlivaMiarma.model.Publicacion;
import com.salesianostriana.dam.DanielOlivaMiarma.repos.PublicacionRepository;
import com.salesianostriana.dam.DanielOlivaMiarma.services.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PublicacionService extends BaseService<Publicacion, UUID, PublicacionRepository> {

    private PublicacionRepository publicacionRepository;

    public List<Publicacion> findAllPublic() {

        return publicacionRepository.findAllPublic();

    }

}
