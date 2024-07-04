package com.librys.bibliotecalibrys.domain.service;

import com.librys.bibliotecalibrys.domain.exception.ClienteNaoEncontradoException;
import com.librys.bibliotecalibrys.domain.exception.CpfClienteEmUsoException;
import com.librys.bibliotecalibrys.domain.model.Cliente;
import com.librys.bibliotecalibrys.domain.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    public Cliente buscar(Long clienteId){

        return clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteNaoEncontradoException(clienteId));

    }

    public List<Cliente> buscarPorNome(String nome){
        List<Cliente> nomePesquisado = clienteRepository.findByNomeContainingIgnoreCase(nome);

        if(nomePesquisado.isEmpty()){
            throw new ClienteNaoEncontradoException("Cliente(s) não encontrado(s).");
        }

        return nomePesquisado;
    }

    public List<Cliente> buscarPorCpf(String cpf){
        List<Cliente> cpfPesquisado = clienteRepository.findByCpfContainingIgnoreCase(cpf);

        if(cpfPesquisado.isEmpty()){
            throw new ClienteNaoEncontradoException("Cliente(s) não encontrado(s).");
        }

        return cpfPesquisado;
    }

    public List<Cliente> buscarPorEmail(String email){
        List<Cliente> emailPesquisado = clienteRepository.findByEmailContainingIgnoreCase(email);

        if(emailPesquisado.isEmpty()){
            throw new ClienteNaoEncontradoException("Cliente(s) não encontrado(s).");
        }

        return emailPesquisado;
    }

    public Cliente adicionar(Cliente cliente) {

        List<Cliente> cpfPesquisado = clienteRepository.findByCpfContainingIgnoreCase(cliente.getCpf());

        if (!cpfPesquisado.isEmpty()) {
            throw new CpfClienteEmUsoException(cliente);
        }

        return clienteRepository.save(cliente);

    }

    public void excluir(Long clienteId){
        Cliente clienteProcurado = clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado."));
        clienteRepository.deleteById(clienteProcurado.getId());
    }

    public Cliente atualizar(Long clinteId, Cliente cliente){

        Cliente clienteAtual = buscar(clinteId);
        BeanUtils.copyProperties(cliente, clienteAtual, "id");

        return  adicionar(clienteAtual);
    }

}