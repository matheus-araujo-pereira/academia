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

import br.com.academia.academia.modelo.Exercicio;
import br.com.academia.academia.servico.ExercicioServico;

@RestController
@RequestMapping("/api/exercicios")
public class ExercicioControlador {

    @Autowired
    private ExercicioServico exercicioServico;

    @PostMapping
    public ResponseEntity<Exercicio> criarExercicio(@RequestBody Exercicio exercicio) {
        Exercicio criado = exercicioServico.save(exercicio);
        return ResponseEntity.ok(criado);
    }

    @GetMapping
    public ResponseEntity<List<Exercicio>> listarExercicios() {
        List<Exercicio> lista = exercicioServico.findAll();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercicio> atualizarExercicio(@PathVariable Long id, @RequestBody Exercicio exercicio) {
        Optional<Exercicio> existente = exercicioServico.findById(id);
        if (existente.isPresent()) {
            exercicio.setId(id);
            Exercicio atualizado = exercicioServico.save(exercicio);
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirExercicio(@PathVariable Long id) {
        Optional<Exercicio> existente = exercicioServico.findById(id);
        if (existente.isPresent()) {
            exercicioServico.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
