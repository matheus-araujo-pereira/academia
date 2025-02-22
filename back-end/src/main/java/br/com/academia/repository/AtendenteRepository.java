package br.com.academia.repository;

import br.com.academia.model.Atendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtendenteRepository extends JpaRepository<Atendente, Integer> {
    List<Atendente> findByNomeContainingIgnoreCase(String nome);
}