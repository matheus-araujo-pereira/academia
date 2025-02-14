package br.com.academia.academia.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academia.academia.modelo.Atividade;
import br.com.academia.academia.repositorio.AtividadeRepositorio;

@Service
public class AtividadeServico {

    @Autowired
    private AtividadeRepositorio atividadeRepositorio;

    public Atividade save(Atividade atividade) {
        return atividadeRepositorio.save(atividade);
    }

    public List<Atividade> findAll() {
        return atividadeRepositorio.findAll();
    }

    public Optional<Atividade> findById(Long id) {
        return atividadeRepositorio.findById(id);
    }

    public void deleteById(Long id) {
        atividadeRepositorio.deleteById(id);
    }
}
