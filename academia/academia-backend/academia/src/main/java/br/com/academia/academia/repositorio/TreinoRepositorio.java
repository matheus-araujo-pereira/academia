package br.com.academia.academia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academia.academia.modelo.Treino;

public interface TreinoRepositorio extends JpaRepository<Treino, Long> {
}
