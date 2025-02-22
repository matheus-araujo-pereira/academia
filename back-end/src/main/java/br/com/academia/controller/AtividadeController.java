package br.com.academia.controller;

import br.com.academia.model.Atividade;
import br.com.academia.service.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @PostMapping
    public ResponseEntity<Atividade> createAtividade(@RequestBody Atividade atividade) {
        Atividade savedAtividade = atividadeService.saveAtividade(atividade);
        return ResponseEntity.ok(savedAtividade);
    }

    @GetMapping
    public ResponseEntity<List<Atividade>> getAllAtividades() {
        List<Atividade> atividades = atividadeService.getAllAtividades();
        return ResponseEntity.ok(atividades);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atividade> updateAtividade(@PathVariable Integer id, @RequestBody Atividade atividadeDetails) {
        Optional<Atividade> updatedAtividade = atividadeService.updateAtividade(id, atividadeDetails);
        return updatedAtividade.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtividade(@PathVariable Integer id) {
        atividadeService.deleteAtividade(id);
        return ResponseEntity.noContent().build();
    }
}