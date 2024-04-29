package com.librys.bibliotecalibrys.domain.exception;


import com.librys.bibliotecalibrys.domain.model.Cliente;

public class CpfEmUsoException extends  RuntimeException{

    private static final long serialVersionUID = 1L;

    public CpfEmUsoException(String mensagem){
        super(mensagem);
    }

    public CpfEmUsoException(Cliente cliente) {
        this(String.format("O CPF '%s' está em uso", cliente.getCpf()));
    }
}
