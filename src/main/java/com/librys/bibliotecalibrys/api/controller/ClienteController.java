package com.librys.bibliotecalibrys.api.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.librys.bibliotecalibrys.domain.model.Cliente;
import com.librys.bibliotecalibrys.domain.service.CadastroClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public Cliente pesquisar(@PathVariable Long clienteId){
        return cadastroCliente.buscar(clienteId);
    }

    @GetMapping("/por-nome")
    public List<Cliente> pesquisarPorNome(@RequestParam String nome){
        return cadastroCliente.buscarPorNome(nome);
    }

    @GetMapping("/por-cpf")
    public List<Cliente> pesquisarPorCpf(@RequestParam String cpf){
        return cadastroCliente.buscarPorCpf(cpf);
    }

    @GetMapping("/por-email")
    public List<Cliente> pesquisarPorEmail(@RequestParam String email){
        return cadastroCliente.buscarPorEmail(email);
    }

    @PostMapping
    public Cliente salvar(@Valid @RequestBody Cliente cliente){
        return cadastroCliente.adicionar(cliente);
    }

    @DeleteMapping("/{clienteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long clienteId){
        cadastroCliente.excluir(clienteId);
    }

    @PutMapping("/{clienteId}")
    public Cliente editar(@PathVariable Long clienteId, @Valid @RequestBody Cliente cliente){
        return cadastroCliente.atualizar(clienteId, cliente);
    }

}