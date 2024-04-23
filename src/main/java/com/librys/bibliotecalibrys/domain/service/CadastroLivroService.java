package com.librys.bibliotecalibrys.domain.service;

import java.util.List;
import java.util.Optional;

import com.librys.bibliotecalibrys.domain.model.Livro;
import com.librys.bibliotecalibrys.domain.repository.LivroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
public class CadastroLivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> listar(){
        return livroRepository.findAll();
    }

    public ResponseEntity<Livro> buscar(Long livroId){

        Optional<Livro> livroPesquisado = livroRepository.findById(livroId);

        if(livroPesquisado.isPresent()) {
            return ResponseEntity.ok(livroPesquisado.get());
        }

        return ResponseEntity.notFound().build();

    }
    
    public ResponseEntity<List<Livro>> buscarPorNome(String nome){
    	List<Livro> livroPesquisado = livroRepository.findByNomeContainingIgnoreCase(nome);
    	
    	if(livroPesquisado.isEmpty()) {
    		return ResponseEntity.notFound().build();
    	}
    	
    	return ResponseEntity.ok(livroPesquisado); 	
    }
    
    public ResponseEntity<List<Livro>> buscarPorAutor(String autor){
    	List<Livro> livroPesquisado = livroRepository.findByAutorContainingIgnoreCase(autor);
    	
    	if(livroPesquisado.isEmpty()) {
    		return ResponseEntity.notFound().build();
    	}
    	return ResponseEntity.ok(livroPesquisado);
    }
   
    public Livro adicionar(Livro livro){
    	return livroRepository.save(livro); 
       
    }

    public ResponseEntity<Livro> excluir(Long livroId){

    	Optional<Livro> livroPesquisado = livroRepository.findById(livroId);
    	
        if (livroPesquisado.isPresent()) {
        	livroRepository.deleteById(livroId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
  
    }

    public ResponseEntity<Livro> atualizar(Long livroId, Livro livro){

        Optional<Livro> livroPesquisado = livroRepository.findById(livroId);

        if(livroPesquisado.isPresent()){

            BeanUtils.copyProperties(livro, livroPesquisado.get(), "id");
            Livro livroSalvo = livroRepository.save(livroPesquisado.get());
            return ResponseEntity.ok(livroSalvo);

        }

        return ResponseEntity.notFound().build();
    }
}
