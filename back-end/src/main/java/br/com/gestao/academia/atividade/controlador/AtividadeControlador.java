package br.com.gestao.academia.atividade.controlador;

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

import br.com.gestao.academia.atividade.modelo.Atividade;
import br.com.gestao.academia.atividade.repositorio.AtividadeRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeControlador {

    private final AtividadeRepositorio repo;

    public AtividadeControlador(AtividadeRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Atividade> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Atividade save(@Valid @RequestBody Atividade atividade) {
        return repo.save(atividade);
    }

    @PutMapping("/{id}")
    public Atividade update(@PathVariable Long id, @Valid @RequestBody Atividade atividade) {
        Atividade existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade não encontrada"));
        existing.setNome(atividade.getNome());
        existing.setDescricao(atividade.getDescricao());
        existing.setHoraInicio(atividade.getHoraInicio());
        existing.setHoraFim(atividade.getHoraFim());
        existing.setDiasSemana(atividade.getDiasSemana());
        existing.setProfessor(atividade.getProfessor());
        existing.setClientes(atividade.getClientes());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
