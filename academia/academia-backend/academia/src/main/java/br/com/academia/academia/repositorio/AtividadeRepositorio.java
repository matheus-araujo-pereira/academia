package br.com.academia.academia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academia.academia.modelo.Atividade;

public interface AtividadeRepositorio extends JpaRepository<Atividade, Long> {
}
