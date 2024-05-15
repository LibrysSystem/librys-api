package com.librys.bibliotecalibrys.domain.service;

import com.librys.bibliotecalibrys.domain.exception.ClienteNaoEncontradoException;
import com.librys.bibliotecalibrys.domain.exception.CpfEmUsoException;
import com.librys.bibliotecalibrys.domain.exception.CpfFuncionarioEmUsoException;
import com.librys.bibliotecalibrys.domain.exception.FuncionarioNaoEncontradoException;
import com.librys.bibliotecalibrys.domain.model.Cliente;
import com.librys.bibliotecalibrys.domain.model.Funcionario;
import com.librys.bibliotecalibrys.domain.repository.FuncionarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroFuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

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

        return funcionarioRepository.save(funcionario);

    }

    public Funcionario atualizar(Funcionario funcionario, Long funcionarioId){
        Funcionario funcionarioPesquisado = buscar(funcionarioId);

        BeanUtils.copyProperties(funcionario, funcionarioPesquisado, "id");
        return adicionar(funcionarioPesquisado);
    }

    public void excluir(Long funcionarioId){
        Funcionario funcionarioPesquisado = buscar(funcionarioId);
        funcionarioRepository.deleteById(funcionarioPesquisado.getId());
    }
}
