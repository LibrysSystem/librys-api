package com.librys.bibliotecalibrys.domain.service;

import com.librys.bibliotecalibrys.domain.exception.FuncionarioNaoEncontradoException;
import com.librys.bibliotecalibrys.domain.model.Funcionario;
import com.librys.bibliotecalibrys.domain.model.Usuario;
import com.librys.bibliotecalibrys.domain.repository.FuncionarioRepository;
import com.librys.bibliotecalibrys.domain.repository.UsuarioRepository;
import com.librys.bibliotecalibrys.domain.enums.RoleName;
import com.librys.bibliotecalibrys.infrastructure.email.EmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private  final EnvioEmailService envioEmail;
    private final FuncionarioRepository funcionarioRepository;

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
    public String redefinirSenha (String email){
        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário não encontrado")));
        try {
            var mensagem = EnvioEmailService.Mensagem.builder()
                    .assunto("Olá!")
                    .corpo("redefinir-senha.html")
                    .variavel("link", "localhost:8080/usuarios/redefinir_senha")
                    .destinatario(usuario.get().getEmail())
                    .build();

            envioEmail.enviar(mensagem);
        } catch (IllegalStateException e){
            throw new EmailException(e.getMessage());
        }

        return "Email enviado com sucesso";
    }

    public void atualizarSenha(Funcionario funcionario){
        Usuario funcionarioAtual = usuarioRepository.findUsuarioByEmail(funcionario.getEmail());

        if(!funcionarioAtual.getPassword().equals(funcionario.getSenha())){
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
