package com.librys.bibliotecalibrys.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LivroAlugado {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    private Livro livro;

    @NotNull
    @ManyToOne
    private Cliente cliente;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate dataLocacao;

    @Column(nullable = false)
    private LocalDate dataDevolucao;

    @NotNull
    @Column(nullable = false)
    private String codigoFiscal;

}
