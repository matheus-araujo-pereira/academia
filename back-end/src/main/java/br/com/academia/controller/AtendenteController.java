package br.com.academia.controller;

import br.com.academia.model.Funcionario;
import br.com.academia.service.AtendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/atendentes")
public class AtendenteController {

    @Autowired
    private AtendenteService atendenteService;

    @PostMapping
    public ResponseEntity<Funcionario> cadastrarAtendente(@RequestBody Funcionario atendente) {
        Funcionario novoAtendente = atendenteService.cadastrarAtendente(atendente);
        return ResponseEntity.ok(novoAtendente);
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> consultarAtendentes(@RequestParam String nome) {
        List<Funcionario> atendentes = atendenteService.consultarAtendentes(nome);
        return ResponseEntity.ok(atendentes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarAtendente(@PathVariable Integer id, @RequestBody Funcionario atendente) {
        Optional<Funcionario> atendenteAtualizado = atendenteService.atualizarAtendente(id, atendente);
        return atendenteAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAtendente(@PathVariable Integer id) {
        atendenteService.excluirAtendente(id);
        return ResponseEntity.noContent().build();
    }
}