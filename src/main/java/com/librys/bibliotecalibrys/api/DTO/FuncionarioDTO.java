package com.librys.bibliotecalibrys.api.DTO;

import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FuncionarioDTO {

    @Id
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String celular;
    private String endereco;
}
