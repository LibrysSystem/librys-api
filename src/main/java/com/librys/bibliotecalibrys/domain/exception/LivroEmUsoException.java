package com.librys.bibliotecalibrys.domain.exception;

public class LivroEmUsoException extends EntidadeEmUsoException{

    private static final long serialVersionUID = 1L;

    public LivroEmUsoException(String mensagem){
        super(mensagem);
    }

    public LivroEmUsoException(Long livroId) {
        this(String.format("O livro '%d' está alugado", livroId));
    }
}
