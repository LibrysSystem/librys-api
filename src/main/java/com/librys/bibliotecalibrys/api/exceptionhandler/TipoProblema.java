package com.librys.bibliotecalibrys.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum TipoProblema {

    PARAMETRO_INVALIDO("Parâmetro inválido"),
    RECURSO_NAO_ENCONTRADO("Recurso não encontrado"),
    ERRO_DE_SISTEMA("Erro de sistema"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensível"),
    ENTIDADE_NAO_ENCONTRADA("Entidade não encontrada"),
    DADOS_INVALIDOS("Dados inválidos"),
    CPF_EM_USO("Cpf em uso");

    private String titulo;

    TipoProblema(String titulo) {
        this.titulo = titulo;
    }
}
