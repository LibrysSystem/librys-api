package com.librys.bibliotecalibrys.api.controller;

import com.librys.bibliotecalibrys.domain.model.Cliente;
import com.librys.bibliotecalibrys.domain.service.CadastroClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private CadastroClienteService cadastroCliente;

    @GetMapping
    public List<Cliente> exibir(){
        return cadastroCliente.listar();
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> pesquisar(@PathVariable Long clienteId){
        return cadastroCliente.buscar(clienteId);
    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<Cliente>> pesquisarPorNome(@RequestParam String nome){
        return cadastroCliente.buscarPorNome(nome);
    }

    @GetMapping("/por-cpf")
    public ResponseEntity<List<Cliente>> pesquisarPorCpf(@RequestParam String cpf){
        return cadastroCliente.buscarPorCpf(cpf);
    }

    @GetMapping("/por-email")
    public ResponseEntity<List<Cliente>> pesquisarPorEmail(@RequestParam String email){
        return cadastroCliente.buscarPorEmail(email);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid Cliente cliente){
        return cadastroCliente.adicionar(cliente);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<?> remover(@PathVariable Long clienteId){
        return cadastroCliente.excluir(clienteId);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<?> editar(@PathVariable Long clienteId, @RequestBody @Valid Cliente cliente){
        return cadastroCliente.atualizar(clienteId, cliente);
    }

}