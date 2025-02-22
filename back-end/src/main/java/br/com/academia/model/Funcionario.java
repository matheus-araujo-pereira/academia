package br.com.academia.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "TB_FUNCIONARIO")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FUNCIONARIO")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_CARGO")
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "ID_TELEFONE")
    private Telefone telefone;

    @Column(name = "NM_FUNCIONARIO")
    private String nome;

    @Column(name = "NR_CPF")
    private String cpf;

    @Column(name = "NR_RG")
    private String rg;

    @Column(name = "DT_NASCIMENTO")
    private Date dataNascimento;

    @Column(name = "EMAIL")
    private String email;
}