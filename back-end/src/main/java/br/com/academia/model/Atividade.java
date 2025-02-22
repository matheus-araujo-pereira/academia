package br.com.academia.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "TB_ATIVIDADE")
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ATIVIDADE")
    private Integer id;

    @Column(name = "NM_ATIVIDADE")
    private String nome;

    @Column(name = "DS_ATIVIDADE")
    private String descricao;

    @Column(name = "DT_INICIO")
    private Timestamp dataInicio;

    @Column(name = "DT_FIM")
    private Timestamp dataFim;
}