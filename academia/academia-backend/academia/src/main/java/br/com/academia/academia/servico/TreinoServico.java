package br.com.academia.academia.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academia.academia.modelo.Treino;
import br.com.academia.academia.repositorio.TreinoRepositorio;

@Service
public class TreinoServico {

    @Autowired
    private TreinoRepositorio treinoRepositorio;

    public Treino save(Treino treino) {
        return treinoRepositorio.save(treino);
    }

    public List<Treino> findAll() {
        return treinoRepositorio.findAll();
    }

    public Optional<Treino> findById(Long id) {
        return treinoRepositorio.findById(id);
    }

    public void deleteById(Long id) {
        treinoRepositorio.deleteById(id);
    }
}
