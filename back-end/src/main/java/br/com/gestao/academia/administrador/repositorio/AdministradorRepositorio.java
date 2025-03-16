package br.com.gestao.academia.administrador.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.administrador.modelo.Administrador;

public interface AdministradorRepositorio extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByLogin(String login);
}
