package br.com.academia.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "TB_FUNCIONARIO")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NM_FUNCIONARIO", nullable = false)
    private String nome;

    @Column(name = "NR_CPF", nullable = false, unique = true)
    private String cpf;

    @Column(name = "NR_RG")
    private String rg;

    @Column(name = "DT_NASCIMENTO")
    private Date dataNascimento;

    @Column(name = "EMAIL")
    private String email;

    @ManyToOne
    @JoinColumn(name = "ID_CARGO")
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "ID_TELEFONE")
    private Telefone telefone;
}