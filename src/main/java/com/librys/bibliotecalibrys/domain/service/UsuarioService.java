package com.librys.bibliotecalibrys.domain.service;

import com.librys.bibliotecalibrys.domain.model.Funcionario;
import com.librys.bibliotecalibrys.domain.model.Usuario;
import com.librys.bibliotecalibrys.domain.repository.UsuarioRepository;
import com.librys.bibliotecalibrys.enums.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> exibirUsuarios(){
        return usuarioRepository.findAll();
    }

    public void registrarLogin(Funcionario funcionario){
        String encriptPassword = new BCryptPasswordEncoder().encode(funcionario.getSenha());
        Usuario usuario = buildUser(funcionario.getEmail(), encriptPassword, RoleName.FUNCIONARIO);
        this.usuarioRepository.save(usuario);
    }

    public void deletar(Funcionario funcionario){
        Usuario usuario = usuarioRepository.findUsuarioByEmail(funcionario.getEmail());
        usuarioRepository.delete(usuario);
    }

    public void atualizarSenha(Funcionario funcionario){
        Usuario funcionarioAtual = usuarioRepository.findUsuarioByEmail(funcionario.getEmail());
        if(!funcionarioAtual.getPassword().equals(funcionario.getSenha())){
            BeanUtils.copyProperties(funcionario.getSenha(), funcionarioAtual.getPassword(), "id");
            registrarLogin(funcionario);
        }
    }

    private Usuario buildUser(String email, String password, RoleName role){
        return Usuario.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();
    }
}
