package br.com.academia.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "TB_PLANO")
public class Plano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PLANO")
    private Integer id;

    @Column(name = "NM_PLANO")
    private String nome;

    @Column(name = "VL_PLANO")
    private BigDecimal valor;

    @Column(name = "DS_PLANO")
    private String descricao;
}