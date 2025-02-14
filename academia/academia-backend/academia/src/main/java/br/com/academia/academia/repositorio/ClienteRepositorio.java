package br.com.academia.academia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academia.academia.modelo.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    List<Cliente> findByCpfContainingIgnoreCase(String cpf);
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}
