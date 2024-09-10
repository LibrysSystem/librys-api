package com.librys.bibliotecalibrys.domain.exception;

public class LivroAlugadoNaoEncontradoException extends EntidadeNaoEncontradaException{

    public LivroAlugadoNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public LivroAlugadoNaoEncontradoException(Long livroAlugadoId){
        this(String.format("Transação número %d de livro alugado não encontrada", livroAlugadoId));
    }
}
