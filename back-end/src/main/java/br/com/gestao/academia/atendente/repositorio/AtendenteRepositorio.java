package br.com.gestao.academia.atendente.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.atendente.modelo.Atendente;

public interface AtendenteRepositorio extends JpaRepository<Atendente, Long> {
}
