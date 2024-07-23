package com.librys.bibliotecalibrys.api.controller;

import com.librys.bibliotecalibrys.domain.model.Funcionario;
import com.librys.bibliotecalibrys.domain.model.Usuario;
import com.librys.bibliotecalibrys.domain.model.UsuarioDTO;
import com.librys.bibliotecalibrys.domain.repository.UsuarioRepository;
import com.librys.bibliotecalibrys.domain.service.UsuarioService;
import com.librys.bibliotecalibrys.security.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final AuthenticationService authenticationService;

    private final UsuarioRepository usuarioRepository;

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public UsuarioDTO authenticate(Authentication authentication){
        return authenticationService.authenticate(authentication);
    }

    @GetMapping("/listar")
    public List<Usuario> listarUsuarios(){
        return usuarioService.exibirUsuarios();
    }

    @DeleteMapping("/deletar")
    public void remover(@RequestBody Funcionario funcionario){
        usuarioService.deletar(funcionario);
    }
}
