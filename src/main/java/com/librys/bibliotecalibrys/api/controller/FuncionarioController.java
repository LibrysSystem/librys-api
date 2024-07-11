package com.librys.bibliotecalibrys.api.controller;

import com.librys.bibliotecalibrys.api.DTO.FuncionarioDTO;
import com.librys.bibliotecalibrys.domain.model.Funcionario;
import com.librys.bibliotecalibrys.domain.service.CadastroFuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private CadastroFuncionarioService cadastroFuncionario;

    @GetMapping
    public List<FuncionarioDTO> exibir(){
        return cadastroFuncionario.listar();
    }

    @GetMapping("/{funcionarioId}")
    public FuncionarioDTO pesquisar(@PathVariable Long funcionarioId){
        return cadastroFuncionario.buscar(funcionarioId);
    }


    @GetMapping("/por-nome")
    public List<FuncionarioDTO> pesquisarPorNome(@RequestParam String nome){
        return cadastroFuncionario.buscarPorNome(nome);
    }

    @GetMapping("/por-cpf")
    public List<FuncionarioDTO> pesquisarPorCpf(@RequestParam String cpf){
        return cadastroFuncionario.buscarPorCpf(cpf);
    }

    @GetMapping("/por-email")
    public List<FuncionarioDTO> pesquisarPorEmail(@RequestParam String email){
        return cadastroFuncionario.buscarPorEmail(email);
    }

    @PostMapping
    public Funcionario salvar(@RequestBody @Valid Funcionario funcionario){
        return cadastroFuncionario.adicionar(funcionario);
    }

    @PutMapping("/{funcionarioId}")
    public Funcionario editar(@RequestBody @Valid Funcionario funcionario, @PathVariable Long funcionarioId){
        return cadastroFuncionario.atualizar(funcionario, funcionarioId);
    }

    @DeleteMapping("/{funcionarioId}")
    public void remover(@PathVariable Long funcionarioId){
        cadastroFuncionario.excluir(funcionarioId);
    }

}
