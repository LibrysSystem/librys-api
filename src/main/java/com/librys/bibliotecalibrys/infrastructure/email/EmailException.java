package com.librys.bibliotecalibrys.infrastructure.email;

public class EmailException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EmailException(String mensagem, Throwable causa){
        super(mensagem, causa);
    }

    public EmailException(String mensagem){
        super(mensagem);
    }
}
