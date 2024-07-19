package com.librys.bibliotecalibrys.api.DTO;

import com.librys.bibliotecalibrys.enums.RoleName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {

    private String token;
    private RoleName role;

}
