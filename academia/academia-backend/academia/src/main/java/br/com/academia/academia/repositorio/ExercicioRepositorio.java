package br.com.academia.academia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academia.academia.modelo.Exercicio;

public interface ExercicioRepositorio extends JpaRepository<Exercicio, Long> {
}
