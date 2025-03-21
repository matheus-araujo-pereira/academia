package br.com.gestao.academia.plano.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.gestao.academia.plano.modelo.Plano;

import java.util.List;

public interface PlanoRepositorio extends JpaRepository<Plano, Long> {
  List<Plano> findByNomeContainingIgnoreCase(String nome);

  @Query("SELECT p FROM Plano p ORDER BY p.valor ASC")
  List<Plano> findAllOrderByValorAsc();
}
