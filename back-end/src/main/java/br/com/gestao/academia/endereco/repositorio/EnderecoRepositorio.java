package br.com.gestao.academia.endereco.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao.academia.endereco.modelo.Endereco;

public interface EnderecoRepositorio extends JpaRepository<Endereco, Long> {
}
