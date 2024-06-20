package com.librys.bibliotecalibrys.api.controller;

import com.librys.bibliotecalibrys.security.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final AuthenticationService authenticationService;

    public UsuarioController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public String authenticate(Authentication authentication){
        return authenticationService.authenticate(authentication);
    }



}
