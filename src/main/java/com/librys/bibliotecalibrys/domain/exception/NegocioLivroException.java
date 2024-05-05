package com.librys.bibliotecalibrys.domain.exception;

public class NegocioLivroException extends NegocioException{

    public  NegocioLivroException(String mensagem){
        super(mensagem);
    }

    public NegocioLivroException(Long livroId){
        this(String.format("Livro %d não cadastrado.", livroId));
    }
}
