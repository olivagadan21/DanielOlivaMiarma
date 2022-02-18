package com.salesianostriana.dam.DanielOlivaMiarma.validacion.validadores;

import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.repos.UsuarioRepository;
import com.salesianostriana.dam.DanielOlivaMiarma.validacion.anotaciones.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UsuarioRepository categoryRepository;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) { }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return !categoryRepository.existsByName(name) && name != null;
    }

}
