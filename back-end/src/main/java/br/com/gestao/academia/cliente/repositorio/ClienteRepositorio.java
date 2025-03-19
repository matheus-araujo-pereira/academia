package br.com.gestao.academia.cliente.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.cliente.modelo.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByLogin(String login);

    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    List<Cliente> findByCpfContainingIgnoreCase(String cpf);

    List<Cliente> findByNomeContainingIgnoreCaseAndCpfContainingIgnoreCase(String nome, String cpf);

    List<Cliente> findByNomeContainingIgnoreCaseOrCpfContainingIgnoreCase(String nome, String cpf);
}
