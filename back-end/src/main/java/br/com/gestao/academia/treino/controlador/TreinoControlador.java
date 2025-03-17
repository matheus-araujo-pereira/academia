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

    public TreinoControlador(TreinoRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Treino> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Treino save(@Valid @RequestBody Treino treino) {
        // Aqui, você pode (na lógica de serviço ou controller) limitar o número de
        // treinos para um cliente a 7.
        return repo.save(treino);
    }

    @PutMapping("/{id}")
    public Treino update(@PathVariable Long id, @Valid @RequestBody Treino treino) {
        Treino existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treino não encontrado"));
        existing.setDescricao(treino.getDescricao());
        existing.setDataCriacao(treino.getDataCriacao());
        existing.setCliente(treino.getCliente());
        existing.setProfessor(treino.getProfessor());
        existing.setExercicios(treino.getExercicios());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
