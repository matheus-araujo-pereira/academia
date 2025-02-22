package br.com.academia.service;

import br.com.academia.model.Treino;
import br.com.academia.repository.TreinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TreinoService {

    @Autowired
    private TreinoRepository treinoRepository;

    public Treino saveTreino(Treino treino) {
        return treinoRepository.save(treino);
    }

    public Optional<Treino> updateTreino(Integer id, Treino treinoDetails) {
        return treinoRepository.findById(id).map(treino -> {
            treino.setNome(treinoDetails.getNome());
            treino.setDescricao(treinoDetails.getDescricao());
            treino.setCliente(treinoDetails.getCliente());
            return treinoRepository.save(treino);
        });
    }
}