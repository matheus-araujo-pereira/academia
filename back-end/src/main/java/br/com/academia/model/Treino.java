package br.com.academia.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "TB_TREINO")
public class Treino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TREINO")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_PROFESSOR")
    private Funcionario professor;

    @Column(name = "DS_TREINO")
    private String descricao;

    @Column(name = "DT_CRIACAO")
    private Timestamp dataCriacao;
}