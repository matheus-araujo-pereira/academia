package br.com.gestao.academia.atendente.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.atendente.modelo.Atendente;

public interface AtendenteRepositorio extends JpaRepository<Atendente, Long> {
    Optional<Atendente> findByLogin(String login);

    List<Atendente> findByNomeContainingIgnoreCaseOrCpfContainingIgnoreCase(String nome, String cpf);
}
