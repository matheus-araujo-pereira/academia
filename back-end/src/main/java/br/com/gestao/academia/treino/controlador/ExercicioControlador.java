package br.com.gestao.academia.treino.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.gestao.academia.treino.modelo.Exercicio;
import br.com.gestao.academia.treino.repositorio.ExercicioRepositorio;
import br.com.gestao.academia.professor.repositorio.ProfessorRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/exercicios")
public class ExercicioControlador {

    private final ExercicioRepositorio repo;
    private final ProfessorRepositorio professorRepo;

    public ExercicioControlador(
            ExercicioRepositorio repo,
            ProfessorRepositorio professorRepo) {
        this.repo = repo;
        this.professorRepo = professorRepo;
    }

    @GetMapping
    public List<Exercicio> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Exercicio save(@RequestParam String login, @Valid @RequestBody Exercicio exercicio) {
        var professorOpt = professorRepo.findByLogin(login);
        if (professorOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas professores podem criar exercícios");
        }
        return repo.save(exercicio);
    }

    @PutMapping("/{id}")
    public Exercicio update(@PathVariable Long id,
            @RequestParam String login,
            @Valid @RequestBody Exercicio exercicio) {
        var professorOpt = professorRepo.findByLogin(login);
        if (professorOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas professores podem editar exercícios");
        }
        Exercicio existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercício não encontrado"));
        existing.setNome(exercicio.getNome());
        existing.setDescricao(exercicio.getDescricao());
        existing.setCarga(exercicio.getCarga());
        existing.setRepeticao(exercicio.getRepeticao());
        existing.setSeries(exercicio.getSeries());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam String login) {
        var professorOpt = professorRepo.findByLogin(login);
        if (professorOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas professores podem excluir exercícios");
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
