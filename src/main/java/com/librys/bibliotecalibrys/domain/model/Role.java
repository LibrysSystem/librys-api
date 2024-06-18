package com.librys.bibliotecalibrys.domain.model;

import com.librys.bibliotecalibrys.enums.RoleName;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "roles")
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName name;



}
