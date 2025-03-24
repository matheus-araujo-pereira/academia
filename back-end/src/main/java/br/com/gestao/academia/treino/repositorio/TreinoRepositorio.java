package br.com.gestao.academia.treino.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.treino.modelo.Treino;

public interface TreinoRepositorio extends JpaRepository<Treino, Long> {
    List<Treino> findByDescricaoContainingIgnoreCase(String descricao);

    List<Treino> findAllByOrderByDataCriacaoAsc();

    List<Treino> findByClienteLogin(String login);

    List<Treino> findByProfessorLogin(String login);
}
