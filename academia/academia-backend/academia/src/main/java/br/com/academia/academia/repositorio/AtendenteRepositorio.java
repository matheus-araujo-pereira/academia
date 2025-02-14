package br.com.academia.academia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academia.academia.modelo.Atendente;

public interface AtendenteRepositorio extends JpaRepository<Atendente, Long> {
    List<Atendente> findByNomeContainingIgnoreCase(String nome);
}
