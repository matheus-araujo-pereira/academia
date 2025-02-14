package br.com.academia.academia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academia.academia.modelo.Professor;

public interface ProfessorRepositorio extends JpaRepository<Professor, Long> {
    List<Professor> findByNomeContainingIgnoreCase(String nome);
}
