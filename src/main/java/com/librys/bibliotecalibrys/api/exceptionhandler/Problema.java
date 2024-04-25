package com.librys.bibliotecalibrys.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problema {

    private Integer status;
    private String tipo;
    private String titulo;
    private String detalhe;
    private String mensagemUsuario;
    private List<Object> objects;

    @Getter
    @Builder
    public static class Object {

        private String nome;
        private String mensagemUsuario;

    }

}
