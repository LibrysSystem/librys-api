package com.librys.bibliotecalibrys.domain.exception;

import com.librys.bibliotecalibrys.domain.model.Cliente;

public class CpfClienteEmUsoException extends CpfEmUsoException{

    public CpfClienteEmUsoException(String mensagem){

        super(mensagem);
    }

    public CpfClienteEmUsoException(Cliente cliente) {
        this(String.format("O CPF '%s' está em uso", cliente.getCpf()));
    }
}
