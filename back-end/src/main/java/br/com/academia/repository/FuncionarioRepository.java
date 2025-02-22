package br.com.academia.repository;

import br.com.academia.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
}