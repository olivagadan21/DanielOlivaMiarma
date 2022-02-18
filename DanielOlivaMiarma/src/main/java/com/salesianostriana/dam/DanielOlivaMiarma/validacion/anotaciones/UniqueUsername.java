package com.salesianostriana.dam.DanielOlivaMiarma.validacion.anotaciones;

import com.salesianostriana.dam.DanielOlivaMiarma.validacion.validadores.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Documented
public @interface UniqueUsername {

    String message() default "El username debe ser único";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}