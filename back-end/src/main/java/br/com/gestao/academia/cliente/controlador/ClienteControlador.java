package br.com.gestao.academia.cliente.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.gestao.academia.cliente.modelo.Cliente;
import br.com.gestao.academia.cliente.repositorio.ClienteRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteControlador {

    private final ClienteRepositorio repo;

    public ClienteControlador(ClienteRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Cliente> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Cliente save(@Valid @RequestBody Cliente cliente) {
        return repo.save(cliente);
    }

    @PutMapping("/{id}")
    public Cliente update(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        Cliente existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        existing.setNome(cliente.getNome());
        existing.setLogin(cliente.getLogin());
        existing.setSenha(cliente.getSenha());
        existing.setCpf(cliente.getCpf());
        existing.setRg(cliente.getRg());
        existing.setDataNascimento(cliente.getDataNascimento());
        existing.setEmail(cliente.getEmail());
        existing.setTelefone(cliente.getTelefone());
        existing.setEndereco(cliente.getEndereco());
        existing.setPlano(cliente.getPlano());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
