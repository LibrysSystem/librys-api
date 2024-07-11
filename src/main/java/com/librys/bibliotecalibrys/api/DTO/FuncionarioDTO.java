package com.librys.bibliotecalibrys.api.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FuncionarioDTO {

    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String celular;
    private String endereco;
}
