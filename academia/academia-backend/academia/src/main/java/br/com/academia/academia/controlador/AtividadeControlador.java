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

import br.com.academia.academia.modelo.Atividade;
import br.com.academia.academia.servico.AtividadeServico;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeControlador {

    @Autowired
    private AtividadeServico atividadeServico;

    // RF16 - Criar Atividade (Administrador)
    @PostMapping
    public ResponseEntity<Atividade> criarAtividade(@RequestBody Atividade atividade) {
        Atividade criado = atividadeServico.save(atividade);
        return ResponseEntity.ok(criado);
    }

    // RF17, RF18, RF19, RF20 - Consultar Atividade
    @GetMapping
    public ResponseEntity<List<Atividade>> listarAtividades() {
        List<Atividade> lista = atividadeServico.findAll();
        return ResponseEntity.ok(lista);
    }

    // RF21 - Atualizar Atividade (Administrador)
    @PutMapping("/{id}")
    public ResponseEntity<Atividade> atualizarAtividade(@PathVariable Long id, @RequestBody Atividade atividade) {
        Optional<Atividade> existente = atividadeServico.findById(id);
        if (existente.isPresent()) {
            atividade.setId(id);
            Atividade atualizado = atividadeServico.save(atividade);
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // RF22 - Excluir Atividade (Administrador)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAtividade(@PathVariable Long id) {
        Optional<Atividade> existente = atividadeServico.findById(id);
        if (existente.isPresent()) {
            atividadeServico.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
