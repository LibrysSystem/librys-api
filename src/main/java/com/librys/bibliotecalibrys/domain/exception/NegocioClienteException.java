package com.librys.bibliotecalibrys.domain.exception;

public class NegocioClienteException extends  NegocioException{

    public NegocioClienteException(String mensagem){
        super(mensagem);
    }

    public NegocioClienteException(Long clienteId){
        this(String.format("Cliente %d não cadastrado", clienteId));
    }
}
