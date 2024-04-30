package com.librys.bibliotecalibrys.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librys.bibliotecalibrys.domain.exception.EntidadeNaoEncontradaException;
import com.librys.bibliotecalibrys.domain.model.LivroAlugado;
import com.librys.bibliotecalibrys.domain.repository.GerenciaLivroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GerenciaLivroService {

    @Autowired
    private GerenciaLivroRepository gerenciaLivroRepository;

    public List<LivroAlugado> listar(){
        return gerenciaLivroRepository.findAll();
    }

    public LivroAlugado adicionar(LivroAlugado gerenciaLivro){
        return gerenciaLivroRepository.save(gerenciaLivro);
    }

    public LivroAlugado atualizar(LivroAlugado gerenciaLivro, Long livroId){
        Optional<LivroAlugado> livroPesquisado = gerenciaLivroRepository.findById(livroId);

        BeanUtils.copyProperties(gerenciaLivro, livroPesquisado.get(), "id");
        return adicionar(livroPesquisado.get());

    }

    public LivroAlugado atualizarParcial(Long gerenciaLivroId, Map<String, Object> campos){

        Optional<LivroAlugado> gerenciaLivroAtual = gerenciaLivroRepository.findById(gerenciaLivroId);


        if(gerenciaLivroAtual.isEmpty()){
            throw new EntidadeNaoEncontradaException("Entidade não encontrada.");
        }

        merge(campos, gerenciaLivroAtual.get());

        return atualizar(gerenciaLivroAtual.get(), gerenciaLivroId);
    }

    public void merge(Map<String, Object> dadosOrigem, LivroAlugado gerenciaLivroNova){
        ObjectMapper objectMapper = new ObjectMapper();
        LivroAlugado gerenciaLivroAntiga = objectMapper.convertValue(dadosOrigem, LivroAlugado.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(LivroAlugado.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, gerenciaLivroAntiga);

            ReflectionUtils.setField(field, gerenciaLivroNova, novoValor);
        });
    }
//    public GerenciaLivro devolver(Long livroId){
//
//    }




}
