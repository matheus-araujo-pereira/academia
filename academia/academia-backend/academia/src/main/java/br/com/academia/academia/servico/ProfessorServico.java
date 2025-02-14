package br.com.academia.academia.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academia.academia.modelo.Professor;
import br.com.academia.academia.repositorio.ProfessorRepositorio;

@Service
public class ProfessorServico {

    @Autowired
    private ProfessorRepositorio professorRepositorio;

    public Professor save(Professor professor) {
        return professorRepositorio.save(professor);
    }

    public List<Professor> findByName(String nome) {
        return professorRepositorio.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Professor> findById(Long id) {
        return professorRepositorio.findById(id);
    }

    public void deleteById(Long id) {
        professorRepositorio.deleteById(id);
    }
}
