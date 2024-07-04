package com.librys.bibliotecalibrys.domain.model;

import com.librys.bibliotecalibrys.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "usuarios")
@Data
@Builder
public class Usuario {

    @Id
    @Column(unique = true)
    private String email;

    private String password;

    private RoleName role;

    public Usuario(String email, String password, RoleName role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Usuario() {

    }
}
