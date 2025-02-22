package br.com.academia.service;

import br.com.academia.model.Atividade;
import br.com.academia.repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    public Atividade saveAtividade(Atividade atividade) {
        return atividadeRepository.save(atividade);
    }

    public List<Atividade> getAllAtividades() {
        return atividadeRepository.findAll();
    }

    public Optional<Atividade> updateAtividade(Integer id, Atividade atividadeDetails) {
        return atividadeRepository.findById(id).map(atividade -> {
            atividade.setNome(atividadeDetails.getNome());
            atividade.setDescricao(atividadeDetails.getDescricao());
            return atividadeRepository.save(atividade);
        });
    }

    public void deleteAtividade(Integer id) {
        atividadeRepository.deleteById(id);
    }
}