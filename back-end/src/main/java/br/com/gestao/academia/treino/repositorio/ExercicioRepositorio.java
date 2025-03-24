package br.com.gestao.academia.treino.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.treino.modelo.Exercicio;

public interface ExercicioRepositorio extends JpaRepository<Exercicio, Long> {
    List<Exercicio> findByNomeContainingIgnoreCase(String nome);

    List<Exercicio> findAllByOrderByIdAsc();
}
