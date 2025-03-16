package br.com.gestao.academia.professor.controlador;

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

import br.com.gestao.academia.professor.modelo.Professor;
import br.com.gestao.academia.professor.repositorio.ProfessorRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/professores")
public class ProfessorControlador {

    private final ProfessorRepositorio repo;

    public ProfessorControlador(ProfessorRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Professor> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Professor save(@Valid @RequestBody Professor professor) {
        return repo.save(professor);
    }

    @PutMapping("/{id}")
    public Professor update(@PathVariable Long id, @Valid @RequestBody Professor professor) {
        Professor existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
        existing.setNome(professor.getNome());
        existing.setLogin(professor.getLogin());
        existing.setSenha(professor.getSenha());
        existing.setCpf(professor.getCpf());
        existing.setRg(professor.getRg());
        existing.setDataNascimento(professor.getDataNascimento());
        existing.setEmail(professor.getEmail());
        existing.setTelefone(professor.getTelefone());
        existing.setEndereco(professor.getEndereco());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}