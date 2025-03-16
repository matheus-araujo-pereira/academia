package br.com.gestao.academia.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String mensagem;
    private String tipoUsuario; // "administrador", "atendente", "professor" ou "cliente"
}
