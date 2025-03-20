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

import br.com.gestao.academia.treino.modelo.Treino;
import br.com.gestao.academia.treino.repositorio.TreinoRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/treinos")
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem
public class TreinoControlador {

    private final TreinoRepositorio repo;

    // Ajuste no construtor para receber os novos repositórios
    public TreinoControlador(
            TreinoRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Treino>> findAll() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Treino>> search(@RequestParam(required = false) String descricao) {
        String d = (descricao == null ? "" : descricao.trim());
        if (d.isEmpty()) {
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(repo.findByDescricaoContainingIgnoreCase(d), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treino> getById(@PathVariable Long id) {
        Treino treino = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treino não encontrado"));
        return new ResponseEntity<>(treino, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Treino> save(@Valid @RequestBody Treino treino) {
        Treino novo = repo.save(treino);
        return new ResponseEntity<>(novo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Treino> update(@PathVariable Long id, @Valid @RequestBody Treino treino) {
        Treino existente = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treino não encontrado"));
        existente.setDescricao(treino.getDescricao());
        existente.setDataCriacao(treino.getDataCriacao());
        existente.setCliente(treino.getCliente());
        existente.setProfessor(treino.getProfessor());
        existente.setExercicios(treino.getExercicios());
        Treino atualizado = repo.save(existente);
        return new ResponseEntity<>(atualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
