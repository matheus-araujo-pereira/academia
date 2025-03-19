package br.com.gestao.academia.administrador.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "administrador", schema = "academia")
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Informe o nome do administrador.")
    private String nome;

    @NotBlank(message = "Informe o login do administrador.")
    private String login;

    @NotBlank(message = "Informe a senha do administrador.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    private String senha;

    @Pattern(regexp = "^[0-9]{11}$", message = "CPF deve ter 11 dígitos")
    private String cpf;

    @NotBlank(message = "RG é obrigatório")
    private String rg;

    @Past(message = "Data de nascimento deve estar no passado")
    private LocalDate dataNascimento;

    @Email(message = "E-mail inválido")
    private String email;

    @Pattern(regexp = "^\\+?\\d{8,15}$", message = "Telefone deve ter entre 8 e 15 dígitos (com ou sem +)")
    private String telefone;
}
