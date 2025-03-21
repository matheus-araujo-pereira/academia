package br.com.gestao.academia.treino.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.treino.modelo.Treino;

import java.util.List;

public interface TreinoRepositorio extends JpaRepository<Treino, Long> {
    List<Treino> findByDescricaoContainingIgnoreCase(String descricao);

    List<Treino> findAllByOrderByDataCriacaoAsc();
}
