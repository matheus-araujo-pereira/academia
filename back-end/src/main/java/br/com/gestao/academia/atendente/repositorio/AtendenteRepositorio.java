package br.com.gestao.academia.atendente.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.atendente.modelo.Atendente;

/**
 * Repositório de Atendente, fornecendo métodos de persistência e consultas
 * customizadas.
 */
public interface AtendenteRepositorio extends JpaRepository<Atendente, Long> {

    /**
     * Retorna um atendente pelo login.
     */
    Optional<Atendente> findByLogin(String login);

    /**
     * Retorna lista de atendentes que possuam nome ou CPF aproximado.
     */
    List<Atendente> findByNomeContainingIgnoreCaseOrCpfContainingIgnoreCase(String nome, String cpf);

    List<Atendente> findByNomeContainingIgnoreCase(String nome);

    List<Atendente> findByCpfContainingIgnoreCase(String cpf);

    List<Atendente> findByNomeContainingIgnoreCaseAndCpfContainingIgnoreCase(String nome, String cpf);
}
