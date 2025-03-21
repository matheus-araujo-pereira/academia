package br.com.gestao.academia.treino.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.treino.modelo.Exercicio;

import java.util.List;

public interface ExercicioRepositorio extends JpaRepository<Exercicio, Long> {
    List<Exercicio> findByNomeContainingIgnoreCase(String nome);

    List<Exercicio> findAllByOrderByIdAsc();
}
