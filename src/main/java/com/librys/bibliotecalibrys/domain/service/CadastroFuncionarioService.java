package com.librys.bibliotecalibrys.domain.service;

import com.librys.bibliotecalibrys.domain.exception.CpfFuncionarioEmUsoException;
import com.librys.bibliotecalibrys.domain.exception.FuncionarioNaoEncontradoException;
import com.librys.bibliotecalibrys.domain.exception.LoginJaRegistrado;
import com.librys.bibliotecalibrys.domain.model.Funcionario;
import com.librys.bibliotecalibrys.domain.repository.FuncionarioRepository;
import com.librys.bibliotecalibrys.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CadastroFuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public List<Funcionario> listar(){
        return funcionarioRepository.findAll();
    }

    public Funcionario buscar(Long funcionarioId){
        return funcionarioRepository.findById(funcionarioId).orElseThrow(() -> new FuncionarioNaoEncontradoException(funcionarioId));
    }

    public List<Funcionario> buscarPorNome(String nome){
        List<Funcionario> nomePesquisado = funcionarioRepository.findByNomeContainingIgnoreCase(nome);

        if(nomePesquisado.isEmpty()){
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado.");
        }

        return nomePesquisado;
    }

    public List<Funcionario> buscarPorCpf(String cpf){
        List<Funcionario> cpfPesquisado = funcionarioRepository.findByCpfContainingIgnoreCase(cpf);

        if(cpfPesquisado.isEmpty()){
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado.");
        }

        return cpfPesquisado;
    }

    public List<Funcionario> buscarPorEmail(String email){
        List<Funcionario> emailPesquisado = funcionarioRepository.findByEmailContainingIgnoreCase(email);

        if(emailPesquisado.isEmpty()){
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado.");
        }

        return emailPesquisado;
    }

    public Funcionario adicionar(Funcionario funcionario){

        List<Funcionario> cpfPesquisado = funcionarioRepository.findByCpfContainingIgnoreCase(funcionario.getCpf());

        if (!cpfPesquisado.isEmpty()) {
            throw new CpfFuncionarioEmUsoException(funcionario);
        }

        if(usuarioRepository.findByEmail(funcionario.getEmail()).isPresent()){
            throw new LoginJaRegistrado("O login desse funcionário já está registrado.");
        }

        usuarioService.registrarLogin(funcionario);

        return funcionarioRepository.save(funcionario);
    }

    public Funcionario atualizar(Funcionario funcionario, Long funcionarioId){
        Funcionario funcionarioPesquisado = buscar(funcionarioId);

        BeanUtils.copyProperties(funcionario, funcionarioPesquisado, "id");
        usuarioService.atualizarSenha(funcionario);

        return funcionarioRepository.save(funcionarioPesquisado);
    }

    public void excluir(Long funcionarioId){
        Funcionario funcionarioPesquisado = buscar(funcionarioId);
        funcionarioRepository.deleteById(funcionarioPesquisado.getId());
        usuarioService.deletar(funcionarioPesquisado);
    }
}
