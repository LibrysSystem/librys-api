package com.librys.bibliotecalibrys.domain.exception;

import com.librys.bibliotecalibrys.domain.model.Funcionario;

public class CpfFuncionarioEmUsoException extends CpfEmUsoException{

    public CpfFuncionarioEmUsoException(String mensagem){

        super(mensagem);
    }

    public CpfFuncionarioEmUsoException(Funcionario funcionario) {
        this(String.format("O CPF '%s' está em uso", funcionario.getCpf()));
    }
}
