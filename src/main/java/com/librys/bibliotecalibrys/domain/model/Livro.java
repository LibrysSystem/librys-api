package com.librys.bibliotecalibrys.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Livro {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(nullable = false)
	private String nome;
	
	@NotNull
	@Column(nullable = false)
	private String autor;
	
	@NotNull
	@Column(nullable = false)
	private String editora;
	
	@NotNull
	@Column(nullable = false)
	private String generoLiterario;
	
	@NotNull
	@Column(nullable = false)
	private String edicao;
	
	@NotNull
	@Column(nullable = false)
	private String codigoLocalizacao;
	
	@NotNull
	@Column(name = "livro_alugado", nullable = false)
	private boolean isAlugado;

	private String imagemUrl;

}
