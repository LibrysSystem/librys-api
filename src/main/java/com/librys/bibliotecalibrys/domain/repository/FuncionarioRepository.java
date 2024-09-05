package com.librys.bibliotecalibrys.domain.repository;

import com.librys.bibliotecalibrys.domain.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
    List<Funcionario> findByCpfContainingIgnoreCase(String cpg);
    Optional<Funcionario> findByEmailContainingIgnoreCase(String email);
    Funcionario findByEmail(String email);

}
