package br.com.academia.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_LOGIN")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LOGIN")
    private Integer id;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "EMAIL")
    private String email;
}