package br.com.gestao.academia.treino.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/exercicios")
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem
public class ExercicioControlador {

    private final ExercicioRepositorio repo;

    public ExercicioControlador(
            ExercicioRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Exercicio>> findAll() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Exercicio>> search(@RequestParam(required = false) String nome) {
        String n = (nome == null ? "" : nome.trim());
        if (n.isEmpty()) {
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(repo.findByNomeContainingIgnoreCase(n), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercicio> getById(@PathVariable Long id) {
        Exercicio exercicio = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercício não encontrado"));
        return new ResponseEntity<>(exercicio, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Exercicio> save(@Valid @RequestBody Exercicio exercicio) {
        Exercicio novo = repo.save(exercicio);
        return new ResponseEntity<>(novo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercicio> update(@PathVariable Long id, @Valid @RequestBody Exercicio exercicio) {
        Exercicio existente = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercício não encontrado"));
        existente.setNome(exercicio.getNome());
        existente.setDescricao(exercicio.getDescricao());
        existente.setCarga(exercicio.getCarga());
        existente.setRepeticao(exercicio.getRepeticao());
        existente.setSeries(exercicio.getSeries());
        Exercicio atualizado = repo.save(existente);
        return new ResponseEntity<>(atualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
