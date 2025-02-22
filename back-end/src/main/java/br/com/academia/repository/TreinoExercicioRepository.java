package br.com.academia.repository;

import br.com.academia.model.TreinoExercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreinoExercicioRepository extends JpaRepository<TreinoExercicio, Integer> {
}