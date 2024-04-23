package com.librys.bibliotecalibrys.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.reflect.FieldUtils;

public class MenorIdadeValidator implements ConstraintValidator<MenorIdade, Object> {

    private String valorField;
    private String nomeResponsavelField;
    private String cpfResponsavelField;
    private String celularResponsavelField;
    private String emailResponsavelField;

    @Override
    public void initialize(MenorIdade constraint) {
        this.valorField = constraint.valorField();
        this.nomeResponsavelField = constraint.nomeResponsavelField();
        this.celularResponsavelField = constraint.celularResponsavelField();
        this.cpfResponsavelField = constraint.cpfResponsavelField();
        this.emailResponsavelField = constraint.emailResponsavelField();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext constraintValidatorContext) {

        try {
            boolean valor = (boolean) FieldUtils.readField(objetoValidacao, valorField, true);
            String campoNome = (String) FieldUtils.readField(objetoValidacao, nomeResponsavelField, true);
            String campoCelular = (String) FieldUtils.readField(objetoValidacao, celularResponsavelField, true);
            String campoCpf = (String) FieldUtils.readField(objetoValidacao, cpfResponsavelField, true);
            String campoEmail = (String) FieldUtils.readField(objetoValidacao, emailResponsavelField, true);

            if((valor) && (campoNome.isBlank() || campoCelular.isBlank() || campoCpf.isBlank() || campoEmail.isBlank())){
                return false;
            }

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

}
