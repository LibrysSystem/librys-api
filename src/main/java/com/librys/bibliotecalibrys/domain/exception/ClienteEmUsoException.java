package com.librys.bibliotecalibrys.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ClienteEmUsoException extends EntidadeEmUsoException {
    private static final long serialVersionUID = 1L;

    public ClienteEmUsoException(String mensagem){
        super(mensagem);
    }

    public ClienteEmUsoException(Long clienteId) {
        this(String.format("Cliente com id %d não pode ser removido pois está com um livro alugado.", clienteId));
    }
}
