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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.academia.academia.modelo.Professor;
import br.com.academia.academia.servico.ProfessorServico;

@RestController
@RequestMapping("/api/professores")
public class ProfessorControlador {

    @Autowired
    private ProfessorServico professorServico;

    // Cadastrar Professor
    @PostMapping
    public ResponseEntity<Professor> cadastrarProfessor(@RequestBody Professor professor) {
        Professor saved = professorServico.save(professor);
        return ResponseEntity.ok(saved);
    }

    // Consultar Professor por nome
    @GetMapping
    public ResponseEntity<List<Professor>> consultarProfessor(
            @RequestParam(value = "nome", defaultValue = "") String nome) {
        List<Professor> lista = professorServico.findByName(nome);
        return ResponseEntity.ok(lista);
    }

    // Atualizar Professor
    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizarProfessor(@PathVariable Long id, @RequestBody Professor professor) {
        Optional<Professor> existente = professorServico.findById(id);
        if (existente.isPresent()) {
            professor.setId(id);
            Professor atualizado = professorServico.save(professor);
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Excluir Professor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProfessor(@PathVariable Long id) {
        Optional<Professor> existente = professorServico.findById(id);
        if (existente.isPresent()) {
            professorServico.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
