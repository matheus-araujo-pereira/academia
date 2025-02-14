package br.com.academia.academia.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academia.academia.modelo.Treino;
import br.com.academia.academia.servico.TreinoServico;

@RestController
@RequestMapping("/api/treinos")
public class TreinoControlador {

    @Autowired
    private TreinoServico treinoServico;

    // RF23 - Criar Treino (Professor)
    @PostMapping
    public ResponseEntity<Treino> criarTreino(@RequestBody Treino treino) {
        Treino criado = treinoServico.save(treino);
        return ResponseEntity.ok(criado);
    }

    // RF24, RF25, RF26 - Consultar Treino
    @GetMapping
    public ResponseEntity<List<Treino>> listarTreinos() {
        List<Treino> lista = treinoServico.findAll();
        return ResponseEntity.ok(lista);
    }

    // RF27 - Atualizar Treino (Professor)
    @PutMapping("/{id}")
    public ResponseEntity<Treino> atualizarTreino(@PathVariable Long id, @RequestBody Treino treino) {
        Optional<Treino> existente = treinoServico.findById(id);
        if (existente.isPresent()) {
            treino.setId(id);
            Treino atualizado = treinoServico.save(treino);
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTreino(@PathVariable Long id) {
        Optional<Treino> existente = treinoServico.findById(id);
        if (existente.isPresent()) {
            treinoServico.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
