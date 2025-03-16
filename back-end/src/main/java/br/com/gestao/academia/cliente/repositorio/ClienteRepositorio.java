package br.com.gestao.academia.cliente.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.cliente.modelo.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
}
