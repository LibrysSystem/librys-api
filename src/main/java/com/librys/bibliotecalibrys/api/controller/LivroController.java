package com.librys.bibliotecalibrys.api.controller;

import java.util.List;

import com.librys.bibliotecalibrys.domain.model.Livro;
import com.librys.bibliotecalibrys.domain.service.CadastroLivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livros")
public class LivroController {

	 @Autowired
	 private CadastroLivroService cadastroLivro;
	 
	 @GetMapping
	 public List<Livro> exibir(){
		 return cadastroLivro.listar();
	 }
	 @GetMapping("/{livroId}")
	 public Livro pesquisar(@PathVariable Long livroId){
	    return cadastroLivro.buscar(livroId);
	 }
	 @GetMapping("/por-nome")
	 public List<Livro> pesquisarPorNome(@RequestParam String nome){
		 return cadastroLivro.buscarPorNome(nome);
	 }
	 
	 @GetMapping("/por-autor")
	 public List<Livro> pesquisarPorAutor(@RequestParam String autor){
		 return cadastroLivro.buscarPorAutor(autor);
	 }
	   
	 @PostMapping
	 @ResponseStatus(HttpStatus.CREATED)
	 public Livro salvar(@Valid @RequestBody Livro livro){
	    return cadastroLivro.adicionar(livro);
	 }

	 @DeleteMapping("/{livroId}")
	 @ResponseStatus(HttpStatus.NO_CONTENT)
	 public void remover(@PathVariable Long livroId) {
		 cadastroLivro.excluir(livroId);
	 }

	 @PutMapping("/{livroId}")
	 public Livro editar(@PathVariable Long livroId, @Valid @RequestBody Livro livro){
	    return cadastroLivro.atualizar(livroId, livro);
	 }

}