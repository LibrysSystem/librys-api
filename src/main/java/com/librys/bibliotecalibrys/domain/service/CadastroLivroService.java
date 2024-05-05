package com.librys.bibliotecalibrys.domain.service;

import java.util.List;

import com.librys.bibliotecalibrys.domain.exception.ClienteNaoEncontradoException;
import com.librys.bibliotecalibrys.domain.exception.LivroEmUsoException;
import com.librys.bibliotecalibrys.domain.exception.LivroNaoEncontradoException;
import com.librys.bibliotecalibrys.domain.model.Livro;
import com.librys.bibliotecalibrys.domain.repository.LivroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;



@Service
public class CadastroLivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> listar(){
        return livroRepository.findAll();
    }

    public Livro buscar(Long livroId){
        return livroRepository.findById(livroId).
                orElseThrow(() -> new LivroNaoEncontradoException(livroId));
    }
    
    public List<Livro> buscarPorNome(String nome) {
        List<Livro> livroPesquisado = livroRepository.findByNomeContainingIgnoreCase(nome);

        if (livroPesquisado.isEmpty()) {
            throw new LivroNaoEncontradoException(
                    String.format("Livro '%s' não encontrado",  nome));
        }
        return livroPesquisado;
    }
    
    public List<Livro> buscarPorAutor(String autor){
    	List<Livro> livroPesquisado = livroRepository.findByAutorContainingIgnoreCase(autor);
    	
    	if(livroPesquisado.isEmpty()) {
    		throw new LivroNaoEncontradoException(String.format("Livro do autor '%s' não encontrado", autor));
    	}
    	return livroPesquisado;
    }
    public Livro adicionar(Livro livro) {
        return livroRepository.save(livro);
    }
    public void excluir(Long livroId){
        try {
            buscar(livroId);
            livroRepository.deleteById(livroId);
        }catch(DataIntegrityViolationException e){
            throw  new LivroEmUsoException(livroId);
        }
    }
    public Livro atualizar(Long livroId, Livro livro){
        Livro livroPesquisado = buscar(livroId);

        BeanUtils.copyProperties(livro, livroPesquisado, "id");
        return livroRepository.save(livroPesquisado);
    }
}
