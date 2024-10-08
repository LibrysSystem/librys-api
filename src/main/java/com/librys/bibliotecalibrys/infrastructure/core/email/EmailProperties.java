package com.librys.bibliotecalibrys.infrastructure.core.email;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("bibliotecalibrys.email")
public class EmailProperties {

    @NotNull
    private String remetente;
}
