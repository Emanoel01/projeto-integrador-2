package br.com.backend.domain.models.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@Builder
@Validated
public class LoginRequest {

    private String email;
    private String password;

}
