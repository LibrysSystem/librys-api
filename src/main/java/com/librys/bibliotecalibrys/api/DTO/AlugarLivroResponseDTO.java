package com.librys.bibliotecalibrys.api.DTO;

import com.librys.bibliotecalibrys.domain.model.Cliente;
import com.librys.bibliotecalibrys.domain.model.Livro;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AlugarLivroResponseDTO {

    private Long id;
    private Livro livro;
    private Cliente cliente;
    private LocalDate dataLocacao;
    private LocalDate dataDevolucao;
}
