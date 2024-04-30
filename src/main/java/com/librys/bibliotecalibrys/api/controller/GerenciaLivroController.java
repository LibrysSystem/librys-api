package com.librys.bibliotecalibrys.api.controller;

import com.librys.bibliotecalibrys.domain.model.LivroAlugado;
import com.librys.bibliotecalibrys.domain.service.GerenciaLivroService;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LivroAlugado alugar(@RequestBody LivroAlugado livroAlugado){
        return gerenciaLivro.adicionar(livroAlugado);

    }

}
