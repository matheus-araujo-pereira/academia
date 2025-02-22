package br.com.academia.repository;

import br.com.academia.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    List<Professor> findByNomeContainingIgnoreCase(String nome);
}