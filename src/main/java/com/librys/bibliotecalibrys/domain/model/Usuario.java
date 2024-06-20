package com.librys.bibliotecalibrys.domain.model;

import com.librys.bibliotecalibrys.enums.RoleName;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Entity(name = "usuarios")
@Data
public class Usuario {

    @Id
    @Column(unique = true)
    private String email;

    private String password;

    private RoleName role;


}
