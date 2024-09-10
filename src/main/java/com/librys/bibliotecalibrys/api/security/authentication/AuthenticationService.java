package com.librys.bibliotecalibrys.api.security.authentication;

import com.librys.bibliotecalibrys.domain.model.Usuario;
import com.librys.bibliotecalibrys.api.DTO.UsuarioDTO;
import com.librys.bibliotecalibrys.api.security.userdetails.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtService jwtService;
    public final UserDetailsService userDetailsService;


    public AuthenticationService(JwtService jwtService, UserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public UsuarioDTO authenticate(Authentication authentication){
        Usuario usuario = ((UserDetailsImpl) authentication.getPrincipal())
                .getUsuario();

        String token = jwtService.generateToken(authentication);
        return UsuarioDTO.builder()
                .role(usuario.getRole())
                .token(token)
                .build();
    }
}
