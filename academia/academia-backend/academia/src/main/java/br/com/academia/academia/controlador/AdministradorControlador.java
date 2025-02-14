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

import br.com.academia.academia.modelo.Administrador;
import br.com.academia.academia.servico.AdministradorServico;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorControlador {

    @Autowired
    private AdministradorServico administradorServico;

    // Cadastrar Administrador
    @PostMapping
    public ResponseEntity<Administrador> cadastrarAdministrador(@RequestBody Administrador admin) {
        Administrador saved = administradorServico.save(admin);
        return ResponseEntity.ok(saved);
    }

    // Consultar Administrador por nome
    @GetMapping
    public ResponseEntity<List<Administrador>> consultarAdministrador(
            @RequestParam(value = "nome", defaultValue = "") String nome) {
        List<Administrador> lista = administradorServico.findByName(nome);
        return ResponseEntity.ok(lista);
    }

    // Atualizar Administrador
    @PutMapping("/{id}")
    public ResponseEntity<Administrador> atualizarAdministrador(@PathVariable Long id,
            @RequestBody Administrador admin) {
        Optional<Administrador> existente = administradorServico.findById(id);
        if (existente.isPresent()) {
            admin.setId(id);
            Administrador atualizado = administradorServico.save(admin);
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Excluir Administrador
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAdministrador(@PathVariable Long id) {
        Optional<Administrador> existente = administradorServico.findById(id);
        if (existente.isPresent()) {
            administradorServico.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
