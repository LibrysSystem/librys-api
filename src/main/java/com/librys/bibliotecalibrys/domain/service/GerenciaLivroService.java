package com.librys.bibliotecalibrys.domain.service;

import com.librys.bibliotecalibrys.api.DTO.AlugarLivroDTO;
import com.librys.bibliotecalibrys.api.DTO.AlugarLivroResponseDTO;
import com.librys.bibliotecalibrys.api.DTO.mapper.AlugarLivroMapper;
import com.librys.bibliotecalibrys.domain.exception.*;
import com.librys.bibliotecalibrys.domain.model.Cliente;
import com.librys.bibliotecalibrys.domain.model.Livro;
import com.librys.bibliotecalibrys.domain.model.LivroAlugado;
import com.librys.bibliotecalibrys.domain.repository.ClienteRepository;
import com.librys.bibliotecalibrys.domain.repository.GerenciaLivroRepository;
import com.librys.bibliotecalibrys.domain.repository.LivroRepository;
import com.librys.bibliotecalibrys.infrastructure.service.email.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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

    @Autowired
    private AlugarLivroMapper mapper;

    public LivroAlugado buscarId(Long livroAlugadoId){
        return gerenciaLivroRepository.findById(livroAlugadoId).orElseThrow(() -> new LivroAlugadoNaoEncontradoException(livroAlugadoId));
    }

    public List<LivroAlugado> listar(){
        return gerenciaLivroRepository.findAll();
    }

    public AlugarLivroResponseDTO adicionar(AlugarLivroDTO alugarLivroDTO){

        LivroAlugado gerenciaLivro = mapper.toEntity(alugarLivroDTO);

        Livro livro = livroRepository.findById(alugarLivroDTO.getLivroId())
                .orElseThrow(() -> new LivroNaoEncontradoException(alugarLivroDTO.getLivroId()));
        Cliente cliente = clienteRepository.findById(alugarLivroDTO.getClienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException(alugarLivroDTO.getClienteId()));

        try{
            livro.setAlugado(true);
            cadastroLivro.atualizar(livro.getId(), livro);

            gerenciaLivro.setLivro(livro);
            gerenciaLivro.setCliente(cliente);
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

            return mapper.toDTO(gerenciaLivro);

        } catch(DataIntegrityViolationException e){
            throw  new LivroEmUsoException(gerenciaLivro.getLivro().getId());
        } catch (IllegalStateException e){
            throw new EmailException(e.getMessage());
        }
    }

    public void excluir(Long gerenciaAlugarId){

        LivroAlugado gerenciaLivro = buscarId(gerenciaAlugarId);
        gerenciaLivroRepository.deleteById(gerenciaAlugarId);

        Livro livro = cadastroLivro.buscar(gerenciaLivro.getLivro().getId());
        livro.setAlugado(false);
        cadastroLivro.atualizar(livro.getId(), livro);

        try{
            var mensagem = EnvioEmailService.Mensagem.builder()
                    .assunto("Olá "+gerenciaLivro.getCliente().getNome()+"!")
                    .corpo("livro-devolvido.html")
                    .variavel("aluguel", gerenciaLivro)
                    .destinatario(gerenciaLivro.getCliente().getEmail())
                    .build();

            envioEmail.enviar(mensagem);
        } catch (IllegalStateException e){
            throw new EmailException(e.getMessage());
        }

    }

    public LivroAlugado atualizar(Long livroId){
        LivroAlugado livroAlugadoPesquisado = buscarId(livroId);

        livroAlugadoPesquisado.setDataLocacao(LocalDate.now());
        livroAlugadoPesquisado.setDataDevolucao(LocalDate.now().plusDays(15));

        gerenciaLivroRepository.save(livroAlugadoPesquisado);

        try {
            var mensagem = EnvioEmailService.Mensagem.builder()
                    .assunto("Olá " + livroAlugadoPesquisado.getCliente().getNome() + "!")
                    .corpo("livro-renovado.html")
                    .variavel("aluguel", livroAlugadoPesquisado)
                    .destinatario(livroAlugadoPesquisado.getCliente().getEmail())
                    .build();

            envioEmail.enviar(mensagem);
        } catch (IllegalStateException e){
            throw new EmailException(e.getMessage());
        }

        return livroAlugadoPesquisado;
    }

    public void buscar(LivroAlugado gerenciaLivro){
        livroRepository.findById(gerenciaLivro
                .getLivro().getId()).orElseThrow(()-> new NegocioLivroException(gerenciaLivro.getLivro().getId()));
        clienteRepository.findById(gerenciaLivro
                .getCliente().getId()).orElseThrow(() -> new NegocioClienteException(gerenciaLivro.getCliente().getId()));

    }

}
