package com.librys.bibliotecalibrys.domain.exception;

public class CpfEmUsoException extends  EntidadeEmUsoException{

    private static final long serialVersionUID = 1L;

    public CpfEmUsoException(String mensagem){
        super(mensagem);
    }

    public CpfEmUsoException(Object object) {
        this(String.format("O CPF '%s' está em uso", object.getClass()));
    }
}
