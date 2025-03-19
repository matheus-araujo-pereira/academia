package br.com.gestao.academia.atendente.modelo;

import java.time.LocalDate;

import br.com.gestao.academia.endereco.modelo.Endereco;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * Entidade que representa um Atendente, contendo informações pessoais,
 * dados de login e vínculo com um Endereco.
 */
@Entity
@Table(name = "atendente", schema = "academia")
public class Atendente {
    /**
     * Identificador único do atendente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome completo do atendente.
     */
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Login é obrigatório")
    private String login;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
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

    /**
     * Estrutura de endereço associada ao atendente.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    @NotNull(message = "Endereço é obrigatório")
    private Endereco endereco;
}
