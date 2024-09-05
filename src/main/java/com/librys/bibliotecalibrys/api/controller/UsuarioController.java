package com.librys.bibliotecalibrys.api.controller;

import com.librys.bibliotecalibrys.domain.model.Funcionario;
import com.librys.bibliotecalibrys.domain.model.Usuario;
import com.librys.bibliotecalibrys.api.DTO.UsuarioDTO;
import com.librys.bibliotecalibrys.domain.service.UsuarioService;
import com.librys.bibliotecalibrys.api.security.authentication.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final AuthenticationService authenticationService;

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public UsuarioDTO authenticate(Authentication authentication){
        return authenticationService.authenticate(authentication);
    }

    @PostMapping("/redefinir-senha")
    public String redefinirSenha(@Valid @RequestBody String email){
        return usuarioService.redefinirSenha(email);
    }

//    @PutMapping("/atualizar-senha")
//    public void atualizarSenha(@Valid @RequestBody Usuario usuario){
//        usuarioService.atualizarSenha(usuario);
//    }

    @GetMapping("/listar")
    public List<Usuario> listarUsuarios(){
        return usuarioService.exibirUsuarios();
    }

    @DeleteMapping("/deletar")
    public void remover(@RequestBody Funcionario funcionario){
        usuarioService.deletar(funcionario);
    }
}
