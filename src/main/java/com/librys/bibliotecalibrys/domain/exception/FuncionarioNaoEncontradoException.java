package com.librys.bibliotecalibrys.domain.exception;

public class FuncionarioNaoEncontradoException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public FuncionarioNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public FuncionarioNaoEncontradoException(Long funcionarioId){
        this(String.format("Funcionario %d não encontrado.", funcionarioId));
    }
}
