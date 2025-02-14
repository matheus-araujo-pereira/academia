package br.com.academia.academia.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academia.academia.modelo.Exercicio;
import br.com.academia.academia.repositorio.ExercicioRepositorio;

@Service
public class ExercicioServico {

    @Autowired
    private ExercicioRepositorio exercicioRepositorio;

    public Exercicio save(Exercicio exercicio) {
        return exercicioRepositorio.save(exercicio);
    }

    public List<Exercicio> findAll() {
        return exercicioRepositorio.findAll();
    }

    public Optional<Exercicio> findById(Long id) {
        return exercicioRepositorio.findById(id);
    }

    public void deleteById(Long id) {
        exercicioRepositorio.deleteById(id);
    }
}
