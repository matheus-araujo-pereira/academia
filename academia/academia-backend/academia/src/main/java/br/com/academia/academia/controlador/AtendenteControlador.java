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

import br.com.academia.academia.modelo.Atendente;
import br.com.academia.academia.servico.AtendenteServico;

@RestController
@RequestMapping("/api/atendentes")
public class AtendenteControlador {

    @Autowired
    private AtendenteServico atendenteServico;

    // Cadastrar Atendente
    @PostMapping
    public ResponseEntity<Atendente> cadastrarAtendente(@RequestBody Atendente atendente) {
        Atendente saved = atendenteServico.save(atendente);
        return ResponseEntity.ok(saved);
    }

    // Consultar Atendente por nome
    @GetMapping
    public ResponseEntity<List<Atendente>> consultarAtendente(
            @RequestParam(value = "nome", defaultValue = "") String nome) {
        List<Atendente> lista = atendenteServico.findByName(nome);
        return ResponseEntity.ok(lista);
    }

    // Atualizar Atendente
    @PutMapping("/{id}")
    public ResponseEntity<Atendente> atualizarAtendente(@PathVariable Long id, @RequestBody Atendente atendente) {
        Optional<Atendente> existente = atendenteServico.findById(id);
        if (existente.isPresent()) {
            atendente.setId(id);
            Atendente atualizado = atendenteServico.save(atendente);
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Excluir Atendente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAtendente(@PathVariable Long id) {
        Optional<Atendente> existente = atendenteServico.findById(id);
        if (existente.isPresent()) {
            atendenteServico.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
