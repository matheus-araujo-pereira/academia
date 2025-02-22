package br.com.academia.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_TREINO_EXERCICIO")
public class TreinoExercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TREINO_EXERCICIO")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_TREINO")
    private Treino treino;

    @ManyToOne
    @JoinColumn(name = "ID_EXERCICIO")
    private Exercicio exercicio;
}