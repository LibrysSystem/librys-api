package com.librys.bibliotecalibrys.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librys.bibliotecalibrys.domain.exception.*;
import com.librys.bibliotecalibrys.domain.model.Livro;
import com.librys.bibliotecalibrys.domain.model.LivroAlugado;
import com.librys.bibliotecalibrys.domain.repository.ClienteRepository;
import com.librys.bibliotecalibrys.domain.repository.GerenciaLivroRepository;
import com.librys.bibliotecalibrys.domain.repository.LivroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GerenciaLivroService {

    @Autowired
    private GerenciaLivroRepository gerenciaLivroRepository;

    @Autowired
    private CadastroLivroService cadastroLivro;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnvioEmailService envioEmail;

    public LivroAlugado buscarId(Long livroAlugadoId){
        return gerenciaLivroRepository.findById(livroAlugadoId).orElseThrow(() -> new LivroAlugadoNaoEncontradoException(livroAlugadoId));
    }

    public List<LivroAlugado> listar(){
        return gerenciaLivroRepository.findAll();
    }

    public LivroAlugado adicionar(LivroAlugado gerenciaLivro){
        buscar(gerenciaLivro);
        try{
            Livro livro = cadastroLivro.buscar(gerenciaLivro.getLivro().getId());
            livro.setAlugado(true);
            cadastroLivro.atualizar(livro.getId(), livro);
            gerenciaLivro.setDataLocacao(LocalDate.now());
            gerenciaLivro.setDataDevolucao(LocalDate.now().plusDays(15));

            var mensagem = EnvioEmailService.Mensagem.builder()
                    .assunto("Olá "+gerenciaLivro.getCliente().getNome()+"!")
                    .corpo("livro-alugado.html")
                    .variavel("aluguel", gerenciaLivro)
                    .destinatario(gerenciaLivro.getCliente().getEmail())
                    .build();

            gerenciaLivro = gerenciaLivroRepository.save(gerenciaLivro);

            envioEmail.enviar(mensagem);


            return gerenciaLivro;
        } catch(DataIntegrityViolationException e){
            throw  new LivroEmUsoException(gerenciaLivro.getLivro().getId());
        }
    }

    public void excluir(Long gerenciaAlugarId){

        LivroAlugado gerenciaLivro = buscarId(gerenciaAlugarId);
            gerenciaLivroRepository.deleteById(gerenciaAlugarId);
            Livro livro = cadastroLivro.buscar(gerenciaLivro.getLivro().getId());
            livro.setAlugado(false);
            cadastroLivro.atualizar(livro.getId(), livro);

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto("Olá "+gerenciaLivro.getCliente().getNome()+"!")
                .corpo("livro-devolvido.html")
                .variavel("aluguel", gerenciaLivro)
                .destinatario(gerenciaLivro.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);
    }

    public LivroAlugado atualizar(LivroAlugado gerenciaLivro, Long livroId){
        LivroAlugado livroAlugadoPesquisado = buscarId(livroId);

        gerenciaLivro.setDataLocacao(LocalDate.now());
        gerenciaLivro.setDataDevolucao(LocalDate.now().plusDays(15));

        BeanUtils.copyProperties(gerenciaLivro, livroAlugadoPesquisado, "id");
        gerenciaLivroRepository.save(livroAlugadoPesquisado);

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto("Olá "+gerenciaLivro.getCliente().getNome()+"!")
                .corpo("livro-renovado.html")
                .variavel("aluguel", gerenciaLivro)
                .destinatario(gerenciaLivro.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);

        return livroAlugadoPesquisado;
    }

    public void buscar(LivroAlugado gerenciaLivro){
        livroRepository.findById(gerenciaLivro
                .getLivro().getId()).orElseThrow(()-> new NegocioLivroException(gerenciaLivro.getLivro().getId()));
        clienteRepository.findById(gerenciaLivro
                .getCliente().getId()).orElseThrow(() -> new NegocioClienteException(gerenciaLivro.getCliente().getId()));

    }

}
