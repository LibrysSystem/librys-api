package com.librys.bibliotecalibrys.api.DTO;

import com.librys.bibliotecalibrys.domain.enums.RoleName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {

    private RoleName role;
    private String token;

}
