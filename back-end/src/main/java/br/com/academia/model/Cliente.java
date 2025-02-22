package br.com.academia.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "TB_CLIENTE")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENTE")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_PLANO")
    private Plano plano;

    @ManyToOne
    @JoinColumn(name = "ID_ENDERECO")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "ID_TREINO")
    private Treino treino;

    @ManyToOne
    @JoinColumn(name = "ID_ATIVIDADE")
    private Atividade atividade;

    @ManyToOne
    @JoinColumn(name = "ID_TELEFONE")
    private Telefone telefone;

    @Column(name = "NM_CLIENTE", nullable = false)
    private String nome;

    @Column(name = "NR_CPF", nullable = false)
    private String cpf;

    @Column(name = "NR_RG")
    private String rg;

    @Column(name = "DT_NASCIMENTO")
    private Date dataNascimento;

    @Column(name = "EMAIL")
    private String email;
}