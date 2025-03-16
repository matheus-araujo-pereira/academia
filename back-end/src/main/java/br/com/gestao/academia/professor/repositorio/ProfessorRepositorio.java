package br.com.gestao.academia.professor.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.professor.modelo.Professor;

public interface ProfessorRepositorio extends JpaRepository<Professor, Long> {
    Optional<Professor> findByLogin(String login);
}
