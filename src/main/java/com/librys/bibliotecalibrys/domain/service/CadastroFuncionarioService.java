package com.librys.bibliotecalibrys.domain.service;

import com.librys.bibliotecalibrys.api.DTO.FuncionarioDTO;
import com.librys.bibliotecalibrys.api.DTO.mapper.FuncionarioMapper;
import com.librys.bibliotecalibrys.domain.exception.CpfFuncionarioEmUsoException;
import com.librys.bibliotecalibrys.domain.exception.FuncionarioNaoEncontradoException;
import com.librys.bibliotecalibrys.domain.exception.LoginJaRegistrado;
import com.librys.bibliotecalibrys.domain.model.Funcionario;
import com.librys.bibliotecalibrys.domain.model.Usuario;
import com.librys.bibliotecalibrys.domain.repository.FuncionarioRepository;
import com.librys.bibliotecalibrys.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CadastroFuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper mapper;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public List<FuncionarioDTO> listar(){
        List<Funcionario> listaFuncionario = new ArrayList<>();
        listaFuncionario = funcionarioRepository.findAll();
        return listaFuncionario.stream()
                .map(mapper::toDTO)
                .toList();
    }

    public FuncionarioDTO buscar(Long funcionarioId){
        return mapper.toDTO(funcionarioRepository.findById(funcionarioId).orElseThrow(() -> new FuncionarioNaoEncontradoException(funcionarioId)));
    }

    public List<FuncionarioDTO> buscarPorNome(String nome){
        List<Funcionario> nomePesquisado = funcionarioRepository.findByNomeContainingIgnoreCase(nome);

        if(nomePesquisado.isEmpty()){
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado.");
        }

        return nomePesquisado.stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<FuncionarioDTO> buscarPorCpf(String cpf){
        List<Funcionario> cpfPesquisado = funcionarioRepository.findByCpfContainingIgnoreCase(cpf);

        if(cpfPesquisado.isEmpty()){
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado.");
        }

        return cpfPesquisado.stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<FuncionarioDTO> buscarPorEmail(String email){
        Optional<Funcionario> emailPesquisado = funcionarioRepository.findByEmailContainingIgnoreCase(email);

        if(emailPesquisado.isEmpty()){
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado.");
        }

        return emailPesquisado.stream()
                .map(mapper::toDTO)
                .toList();
    }

    public FuncionarioDTO adicionar(Funcionario funcionario){

        List<Funcionario> cpfPesquisado = funcionarioRepository.findByCpfContainingIgnoreCase(funcionario.getCpf());

        if (!cpfPesquisado.isEmpty()) {
            throw new CpfFuncionarioEmUsoException(funcionario);
        }

        if(usuarioRepository.findByEmail(funcionario.getEmail()).isPresent()){
            throw new LoginJaRegistrado("O login desse funcionário já está registrado.");
        }

        usuarioService.registrarLogin(funcionario);
        funcionarioRepository.save(funcionario);

        return mapper.toDTO(funcionario);
    }

    public FuncionarioDTO atualizar(Funcionario funcionario){
        Funcionario funcionarioPesquisado = funcionarioRepository.findById(funcionario.getId()).orElseThrow();
        Usuario funcionarioAtual = usuarioRepository.findUsuarioByEmail(funcionario.getEmail());

        BeanUtils.copyProperties(funcionario, funcionarioPesquisado, "id");
        usuarioService.atualizarSenha(funcionarioAtual);

        funcionarioRepository.save(funcionarioPesquisado);

        return mapper.toDTO(funcionarioPesquisado);
    }

    public void excluir(Long funcionarioId){
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId).orElseThrow();
        funcionarioRepository.deleteById(funcionarioId);
        usuarioService.deletar(funcionario);
    }
}
