package com.librys.bibliotecalibrys.domain.model;

import com.librys.bibliotecalibrys.api.validation.MenorIdade;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CPF;

@MenorIdade(valorField = "menorDeIdade", nomeResponsavelField = "nomeDoResponsavel", cpfResponsavelField = "cpfDoResponsavel",
        celularResponsavelField = "celularDoResponsavel", emailResponsavelField = "emailDoResponsavel")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cliente {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String nome;

    @CPF
    @Column(nullable = false, unique = true)
    private String cpf;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private String dataNascimento;

    @NotNull
    @Column(nullable = false)
    private String celular;

    @NotNull
    @Email
    @Column(nullable = false)
    private String email;

    private String endereco;

    @NotNull
    @Column(name = "menor_idade", nullable = false)
    private boolean menorDeIdade;

    private String nomeDoResponsavel;

    private String celularDoResponsavel;

    @Email
    private String emailDoResponsavel;

    @CPF
    private String cpfDoResponsavel;

}