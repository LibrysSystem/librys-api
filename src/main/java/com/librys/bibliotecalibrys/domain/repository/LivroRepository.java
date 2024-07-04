package com.librys.bibliotecalibrys.domain.repository;

import com.librys.bibliotecalibrys.domain.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LivroRepository extends JpaRepository<Livro, Long>{
	
	List<Livro> findByNomeContainingIgnoreCase(String nome);
	List<Livro> findByAutorContainingIgnoreCase(String autor);

}
