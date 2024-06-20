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
        Usuario usuario = new Usuario();
        usuario.setEmail("admin");
        usuario.setPassword("$2a$12$Uq6x7EotWOfX.MRo46KkS.hfoEGCEW0kZl1TG6q0aM9ZuThn9mtmq");
        usuario.setRole(RoleName.ROLE_SUPORTE);
        usuarioRepository.save(usuario);
    }
}
