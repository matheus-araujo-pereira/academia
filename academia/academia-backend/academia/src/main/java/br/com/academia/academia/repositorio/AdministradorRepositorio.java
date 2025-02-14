package br.com.academia.academia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academia.academia.modelo.Administrador;

public interface AdministradorRepositorio extends JpaRepository<Administrador, Long> {
    List<Administrador> findByNomeContainingIgnoreCase(String nome);
}
