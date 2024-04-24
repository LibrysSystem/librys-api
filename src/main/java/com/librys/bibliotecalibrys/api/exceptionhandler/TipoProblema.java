package com.librys.bibliotecalibrys.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum TipoProblema {

    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado");

    private String titulo;
    private String uri;

    TipoProblema(String uri, String titulo) {
        this.uri = "https://librys.com.br" + uri;
        this.titulo = titulo;
    }
}
