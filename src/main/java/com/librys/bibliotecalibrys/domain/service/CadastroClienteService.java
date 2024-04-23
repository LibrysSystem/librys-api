package com.librys.bibliotecalibrys.domain.service;

import com.librys.bibliotecalibrys.domain.exception.EntidadeNaoEncontradaException;
import com.librys.bibliotecalibrys.domain.model.Cliente;
import com.librys.bibliotecalibrys.domain.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    public ResponseEntity<Cliente> buscar(Long clienteId){

        Optional<Cliente> cliente = clienteRepository.findById(clienteId);

        if(cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }

        return ResponseEntity.notFound().build();

    }

    public ResponseEntity<List<Cliente>> buscarPorNome(String nome){
        List<Cliente> nomePesquisado = clienteRepository.findByNomeContainingIgnoreCase(nome);

        if(nomePesquisado.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(nomePesquisado);
    }

    public ResponseEntity<List<Cliente>> buscarPorCpf(String cpf){
        List<Cliente> cpfPesquisado = clienteRepository.findByCpfContainingIgnoreCase(cpf);

        if(cpfPesquisado.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cpfPesquisado);
    }

    public ResponseEntity<List<Cliente>> buscarPorEmail(String email){
        List<Cliente> emailPesquisado = clienteRepository.findByEmailContainingIgnoreCase(email);

        if(emailPesquisado.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(emailPesquisado);
    }

    public ResponseEntity<?> adicionar(Cliente cliente){
        try {
            cliente = clienteRepository.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(cliente);

        } catch (DataIntegrityViolationException e){
            return ResponseEntity.badRequest().body("Erro: dado(s) incorreto(s)");
        }
    }

    public ResponseEntity<?> excluir(Long clienteId){

        if (!clienteRepository.existsById(clienteId)) {
            throw new EntidadeNaoEncontradaException(String.format("Cliente com código %d não encontrado.", clienteId));
        }

        clienteRepository.deleteById(clienteId);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> atualizar(Long clinteId, Cliente cliente){

        Optional<Cliente> clienteAtual = clienteRepository.findById(clinteId);

        if(clienteAtual.isPresent()){

            BeanUtils.copyProperties(cliente, clienteAtual.get(), "id");
            Cliente clienteSalvo = clienteRepository.save(clienteAtual.get());
            return ResponseEntity.ok(clienteSalvo);

        }

        return ResponseEntity.notFound().build();
    }
}