package com.librys.bibliotecalibrys.seeders;

import com.librys.bibliotecalibrys.domain.model.Usuario;
import com.librys.bibliotecalibrys.domain.repository.UsuarioRepository;
import com.librys.bibliotecalibrys.enums.RoleName;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {

    UsuarioRepository usuarioRepository;

    public DatabaseSeeder(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event){
        seedSave();
    }

    void seedSave(){
        Usuario usuario = Usuario.builder()
                .email("bibliotecalibrys@gmail.com")
                .password("$2a$12$d7XgSKR3FLEiTyMeZQ4kyeNzCeUfQT66hQym8L6czWQX9MRh/cLFO")
                .role(RoleName.SUPORTE).
                build();
        usuarioRepository.save(usuario);
    }
}
