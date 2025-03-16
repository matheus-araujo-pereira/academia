package br.com.gestao.academia.treino.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.treino.modelo.Exercicio;

public interface ExercicioRepositorio extends JpaRepository<Exercicio, Long> {
}
