package br.com.academia.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_ENDERECO")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENDERECO")
    private Integer id;

    @Column(name = "DS_ESTADO")
    private String estado;

    @Column(name = "DS_CIDADE")
    private String cidade;

    @Column(name = "DS_BAIRRO")
    private String bairro;

    @Column(name = "DS_LOGRADOURO")
    private String logradouro;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "CEP")
    private String cep;
}