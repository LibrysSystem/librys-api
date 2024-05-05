package com.librys.bibliotecalibrys.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librys.bibliotecalibrys.domain.exception.EntidadeNaoEncontradaException;
import com.librys.bibliotecalibrys.domain.exception.LivroAlugadoNaoEncontradoException;
import com.librys.bibliotecalibrys.domain.exception.NegocioClienteException;
import com.librys.bibliotecalibrys.domain.exception.NegocioLivroException;
import com.librys.bibliotecalibrys.domain.model.LivroAlugado;
import com.librys.bibliotecalibrys.domain.repository.GerenciaLivroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public LivroAlugado buscarId(Long livroAlugadoId){
        return gerenciaLivroRepository.findById(livroAlugadoId).orElseThrow(() -> new LivroAlugadoNaoEncontradoException(livroAlugadoId));
    }

    public LivroAlugado buscar(LivroAlugado gerenciaLivro){
            gerenciaLivroRepository.findById(gerenciaLivro
                    .getLivro().getId()).orElseThrow(()-> new NegocioLivroException(gerenciaLivro.getLivro().getId()));
            gerenciaLivroRepository.findById(gerenciaLivro
                    .getCliente().getId()).orElseThrow(() -> new NegocioClienteException(gerenciaLivro.getCliente().getId()));

            return buscarId(gerenciaLivro.getId());
    }

    public List<LivroAlugado> listar(){
        return gerenciaLivroRepository.findAll();
    }

    public LivroAlugado adicionar(LivroAlugado gerenciaLivro){
        buscar(gerenciaLivro);

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
