package br.com.gestao.academia.login.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO que recebe o login e a senha do usuário para validação.
 */
@Data
public class LoginRequest {
    @NotBlank(message = "Login é obrigatório")
    private String login;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;
}
