package br.com.academia.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_EXERCICIO")
public class Exercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EXERCICIO")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_PROFESSOR")
    private Funcionario professor;

    @Column(name = "NM_EXERCICIO")
    private String nome;

    @Column(name = "DS_EXERCICIO")
    private String descricao;

    @Column(name = "CARGA")
    private Float carga;

    @Column(name = "REPETICOES")
    private Integer repeticoes;

    @Column(name = "SERIES")
    private Integer series;
}