package br.com.gestao.academia.professor.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao.academia.professor.modelo.Professor;
import br.com.gestao.academia.professor.repositorio.ProfessorRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/professores")
public class ProfessorControlador {

    private final ProfessorRepositorio repo;

    public ProfessorControlador(ProfessorRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Professor> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Professor save(@Valid @RequestBody Professor professor) {
        return repo.save(professor);
    }
}
