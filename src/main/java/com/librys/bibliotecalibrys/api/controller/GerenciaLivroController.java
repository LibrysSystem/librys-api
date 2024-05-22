package com.librys.bibliotecalibrys.api.controller;

import com.librys.bibliotecalibrys.domain.model.LivroAlugado;
import com.librys.bibliotecalibrys.domain.service.GerenciaLivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gerencia-livro")
public class GerenciaLivroController {

    @Autowired
    private GerenciaLivroService gerenciaLivro;
    
    @GetMapping
    public List<LivroAlugado> exibir(){
        return gerenciaLivro.listar();
    }

    @GetMapping("/{livroAlugadoId}")
    public LivroAlugado pesquisar(@PathVariable Long livroAlugadoId){
        return gerenciaLivro.buscarId(livroAlugadoId);
    }

    @PostMapping("/alugar")
    @ResponseStatus(HttpStatus.CREATED)
    public LivroAlugado alugar(@RequestBody @Valid LivroAlugado livroAlugado){
        return gerenciaLivro.adicionar(livroAlugado);

    }

    @DeleteMapping("/devolver/{livroAlugadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void devolver(@PathVariable Long livroAlugadoId){
        gerenciaLivro.excluir(livroAlugadoId);
    }

    @PutMapping("/renovar/{livroAlugadoId}")
    public LivroAlugado renovar(@PathVariable Long livroAlugadoId){
        return gerenciaLivro.atualizar(livroAlugadoId);
    }

}
