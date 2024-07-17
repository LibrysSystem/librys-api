package com.librys.bibliotecalibrys.domain.model;

import com.librys.bibliotecalibrys.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {

    private RoleName role;
    private String token;

}
