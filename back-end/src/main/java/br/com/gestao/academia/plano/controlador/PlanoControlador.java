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
    public List<Plano> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Plano save(@Valid @RequestBody Plano plano) {
        return repo.save(plano);
    }

    @PutMapping("/{id}")
    public Plano update(@PathVariable Long id, @Valid @RequestBody Plano plano) {
        Plano existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));
        existing.setNome(plano.getNome());
        existing.setValor(plano.getValor());
        existing.setDescricao(plano.getDescricao());
        existing.setAdministrador(plano.getAdministrador());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
