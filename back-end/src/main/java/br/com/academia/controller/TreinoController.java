package br.com.academia.controller;

import br.com.academia.model.Treino;
import br.com.academia.service.TreinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/treinos")
public class TreinoController {

    @Autowired
    private TreinoService treinoService;

    @PostMapping
    public ResponseEntity<Treino> createTreino(@RequestBody Treino treino) {
        Treino savedTreino = treinoService.saveTreino(treino);
        return ResponseEntity.ok(savedTreino);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Treino> updateTreino(@PathVariable Integer id, @RequestBody Treino treinoDetails) {
        Optional<Treino> updatedTreino = treinoService.updateTreino(id, treinoDetails);
        return updatedTreino.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}