package br.com.academia.controller;

import br.com.academia.model.Funcionario;
import br.com.academia.model.Professor;
import br.com.academia.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private ProfessorService professorService;

    @PostMapping
    public ResponseEntity<Funcionario> cadastrarProfessor(@RequestBody Funcionario atendente) {
        Funcionario novoAtendente = professorService.cadastrarProfessor(atendente);
        return ResponseEntity.ok(novoAtendente);
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> consultarProfessores(@RequestParam String nome) {
        List<Funcionario> atendentes = professorService.consultarProfessores(nome);
        return ResponseEntity.ok(atendentes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarProfessor(@PathVariable Integer id, @RequestBody Funcionario atendente) {
        Optional<Funcionario> atendenteAtualizado = professorService.atualizarProfessor(id, atendente);
        return atendenteAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProfessor(@PathVariable Integer id) {
        professorService.excluirProfessor(id);
        return ResponseEntity.noContent().build();
    }
}