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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.gestao.academia.treino.modelo.Exercicio;
import br.com.gestao.academia.treino.repositorio.ExercicioRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/exercicios")
public class ExercicioControlador {

    private final ExercicioRepositorio repo;

    public ExercicioControlador(ExercicioRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Exercicio> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Exercicio save(@Valid @RequestBody Exercicio exercicio) {
        return repo.save(exercicio);
    }

    @PutMapping("/{id}")
    public Exercicio update(@PathVariable Long id, @Valid @RequestBody Exercicio exercicio) {
        Exercicio existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercício não encontrado"));
        existing.setNome(exercicio.getNome());
        existing.setDescricao(exercicio.getDescricao());
        existing.setCarga(exercicio.getCarga());
        existing.setRepeticao(exercicio.getRepeticao());
        existing.setSeries(exercicio.getSeries());
        // Se necessário, pode atualizar a associação com o treino
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
