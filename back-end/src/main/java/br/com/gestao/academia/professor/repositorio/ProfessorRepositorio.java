/**
 * Interface do repositório para operações de CRUD com a entidade Professor.
 */
package br.com.gestao.academia.professor.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.professor.modelo.Professor;

public interface ProfessorRepositorio extends JpaRepository<Professor, Long> {
    Optional<Professor> findByLogin(String login);

    List<Professor> findByNomeContainingIgnoreCase(String nome);

    List<Professor> findByCpfContainingIgnoreCase(String cpf);

    List<Professor> findByNomeContainingIgnoreCaseAndCpfContainingIgnoreCase(String nome, String cpf);

    List<Professor> findAllByOrderByNomeAsc();

    boolean existsByCpf(String cpf);

    boolean existsByRg(String rg);

    boolean existsByLogin(String login);
}
