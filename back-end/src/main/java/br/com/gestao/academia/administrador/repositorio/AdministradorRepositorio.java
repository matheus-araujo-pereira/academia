package br.com.gestao.academia.administrador.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.administrador.modelo.Administrador;

public interface AdministradorRepositorio extends JpaRepository<Administrador, Long> {
}
