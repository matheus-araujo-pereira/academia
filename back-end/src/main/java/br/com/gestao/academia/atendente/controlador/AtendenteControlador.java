package br.com.gestao.academia.atendente.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.gestao.academia.atendente.modelo.Atendente;
import br.com.gestao.academia.atendente.repositorio.AtendenteRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/atendentes")
@CrossOrigin(origins = "*") // Para permitir CORS
public class AtendenteControlador {

    private final AtendenteRepositorio repo;

    public AtendenteControlador(AtendenteRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Atendente> findAll() {
        return repo.findAll();
    }

    @GetMapping("/search")
    public List<Atendente> search(@RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf) {
        if ((nome == null || nome.isEmpty()) && (cpf == null || cpf.isEmpty())) {
            return repo.findAll();
        }
        return repo.findByNomeContainingIgnoreCaseOrCpfContainingIgnoreCase(
                nome == null ? "" : nome,
                cpf == null ? "" : cpf);
    }

    @GetMapping("/{id}")
    public Atendente getById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atendente não encontrado"));
    }

    @PostMapping
    public Atendente create(@Valid @RequestBody Atendente atendente) {
        return repo.save(atendente);
    }

    @PutMapping("/{id}")
    public Atendente update(@PathVariable Long id, @Valid @RequestBody Atendente atendente) {
        Atendente existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atendente não encontrado"));
        existing.setNome(atendente.getNome());
        existing.setLogin(atendente.getLogin());
        existing.setSenha(atendente.getSenha());
        existing.setCpf(atendente.getCpf());
        existing.setRg(atendente.getRg());
        existing.setDataNascimento(atendente.getDataNascimento());
        existing.setEmail(atendente.getEmail());
        existing.setTelefone(atendente.getTelefone());
        existing.setEndereco(atendente.getEndereco());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
