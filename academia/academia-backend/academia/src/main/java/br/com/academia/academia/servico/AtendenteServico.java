package br.com.academia.academia.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academia.academia.modelo.Atendente;
import br.com.academia.academia.repositorio.AtendenteRepositorio;

@Service
public class AtendenteServico {

    @Autowired
    private AtendenteRepositorio atendenteRepositorio;

    public Atendente save(Atendente atendente) {
        return atendenteRepositorio.save(atendente);
    }

    public List<Atendente> findByName(String nome) {
        return atendenteRepositorio.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Atendente> findById(Long id) {
        return atendenteRepositorio.findById(id);
    }

    public void deleteById(Long id) {
        atendenteRepositorio.deleteById(id);
    }
}
