package com.librys.bibliotecalibrys.domain.repository;

import com.librys.bibliotecalibrys.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    List<Cliente> findByCpfContainingIgnoreCase(String cpg);
    List<Cliente> findByEmailContainingIgnoreCase(String email);

}