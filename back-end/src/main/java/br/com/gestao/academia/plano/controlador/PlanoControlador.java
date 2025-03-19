package br.com.gestao.academia.plano.controlador;

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

import br.com.gestao.academia.plano.modelo.Plano;
import br.com.gestao.academia.plano.repositorio.PlanoRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/planos")
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem
public class PlanoControlador {

    private final PlanoRepositorio repo;

    public PlanoControlador(PlanoRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Plano>> findAll() {
        try {
            List<Plano> planos = repo.findAll();
            return new ResponseEntity<>(planos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Plano>> search(@RequestParam(required = false) String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return findAll();
        }
        List<Plano> planos = repo.findByNomeContainingIgnoreCase(nome.trim());
        return new ResponseEntity<>(planos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Plano plano) {
        try {
            Plano novo = repo.save(plano);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Plano cadastrado com sucesso! ID: " + novo.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao cadastrar plano: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Plano plano) {
        try {
            Plano existing = repo.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));
            existing.setNome(plano.getNome());
            existing.setValor(plano.getValor());
            existing.setDescricao(plano.getDescricao());
            existing.setAdministrador(plano.getAdministrador());
            repo.save(existing);
            return ResponseEntity.ok("Plano atualizado com sucesso!");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar plano: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            repo.deleteById(id);
            return ResponseEntity.ok("Plano excluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao excluir plano: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plano> getById(@PathVariable Long id) {
        Plano plano = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));
        return ResponseEntity.ok(plano);
    }
}
