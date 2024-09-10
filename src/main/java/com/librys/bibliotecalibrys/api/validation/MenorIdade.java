package com.librys.bibliotecalibrys.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MenorIdadeValidator.class})
public @interface MenorIdade {
    String message() default "Os campos referente ao responsável devem ser preenchidos.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String valorField();

    String nomeResponsavelField();

    String cpfResponsavelField();

    String celularResponsavelField();

    String emailResponsavelField();

}
