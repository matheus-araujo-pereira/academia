package br.com.gestao.academia.atividade.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.atividade.modelo.Atividade;

public interface AtividadeRepositorio extends JpaRepository<Atividade, Long> {
}
