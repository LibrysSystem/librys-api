package com.librys.bibliotecalibrys.domain.exception;

import com.librys.bibliotecalibrys.domain.model.Cliente;
import com.librys.bibliotecalibrys.domain.model.Livro;

import java.util.List;

public class LivroEmUsoException extends EntidadeEmUsoException{

    private static final long serialVersionUID = 1L;

    public LivroEmUsoException(String mensagem){
        super(mensagem);
    }

    public LivroEmUsoException(Livro livro) {
        this(String.format("O livro '%d' está em uso", livro.getId()));
    }
}
