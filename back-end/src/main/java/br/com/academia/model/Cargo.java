package br.com.academia.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_CARGO")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CARGO")
    private Integer id;

    @Column(name = "CD_CARGO", unique = true)
    private Integer codigo;

    @Column(name = "NM_CARGO", unique = true)
    private String nome;

    @Column(name = "DS_CARGO")
    private String descricao;
}