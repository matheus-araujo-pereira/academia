package br.com.academia.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_USUARIO")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_LOGIN")
    private Login login;

    @ManyToOne
    @JoinColumn(name = "ID_FUNCIONARIO")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;
}