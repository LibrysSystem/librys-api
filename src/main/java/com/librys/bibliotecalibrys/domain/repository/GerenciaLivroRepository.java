package com.librys.bibliotecalibrys.domain.repository;

import com.librys.bibliotecalibrys.domain.model.LivroAlugado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GerenciaLivroRepository extends JpaRepository<LivroAlugado, Long> {

}
