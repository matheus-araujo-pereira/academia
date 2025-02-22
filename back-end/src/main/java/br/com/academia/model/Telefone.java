package br.com.academia.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_TELEFONE")
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TELEFONE")
    private Integer id;

    @Column(name = "NR_CELULAR")
    private String celular;

    @Column(name = "NR_TELEFONE_FIXO")
    private String telefoneFixo;
}