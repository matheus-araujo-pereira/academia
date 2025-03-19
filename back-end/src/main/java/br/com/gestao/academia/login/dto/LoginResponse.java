package br.com.gestao.academia.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO usado para retornar a mensagem de status e o tipo de usuário após o
 * login.
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    private String mensagem;
    private String tipoUsuario; // "administrador", "atendente", "professor" ou "cliente"
}
