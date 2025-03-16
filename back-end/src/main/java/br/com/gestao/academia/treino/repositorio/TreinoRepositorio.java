package br.com.gestao.academia.treino.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.treino.modelo.Treino;

public interface TreinoRepositorio extends JpaRepository<Treino, Long> {
}
