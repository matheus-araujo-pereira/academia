/**
 * Controlador REST para gerenciar professores.
 * Disponibiliza endpoints para listar, buscar, criar, atualizar e excluir professores.
 * 
 * @author 
 */
package br.com.gestao.academia.professor.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import br.com.gestao.academia.professor.modelo.Professor;
import br.com.gestao.academia.professor.repositorio.ProfessorRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/professores")
@CrossOrigin(origins = "*") // Para permitir CORS
public class ProfessorControlador {

    private final ProfessorRepositorio repo;

    public ProfessorControlador(ProfessorRepositorio repo) {
        this.repo = repo;
    }

    /**
     * Retorna a lista de todos os professores.
     */
    @GetMapping
    public ResponseEntity<List<Professor>> findAll() {
        return new ResponseEntity<>(repo.findAllByOrderByNomeAsc(), HttpStatus.OK);
    }

    /**
     * Busca professores filtrando por nome e/ou CPF.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Professor>> search(@RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf) {
        String n = (nome == null ? "" : nome.trim());
        String c = (cpf == null ? "" : cpf.trim());

        List<Professor> result;
        if (n.isEmpty() && c.isEmpty()) {
            result = repo.findAll();
        } else if (!n.isEmpty() && !c.isEmpty()) {
            result = repo.findByNomeContainingIgnoreCaseAndCpfContainingIgnoreCase(n, c);
        } else if (!n.isEmpty()) {
            result = repo.findByNomeContainingIgnoreCase(n);
        } else {
            result = repo.findByCpfContainingIgnoreCase(c);
        }
        // Ordena a lista de professores por nome (ordem alfabética)
        result.sort((a, b) -> a.getNome().compareToIgnoreCase(b.getNome()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retorna um professor pelo seu ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Professor> getById(@PathVariable Long id) {
        Professor professor = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
        return new ResponseEntity<>(professor, HttpStatus.OK);
    }

    /**
     * Cria um novo professor.
     */
    @PostMapping
    public ResponseEntity<Professor> save(@Valid @RequestBody Professor professor) {
        if (repo.existsByCpf(professor.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já está em uso");
        }
        if (repo.existsByRg(professor.getRg())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RG já está em uso");
        }
        if (repo.existsByLogin(professor.getLogin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login já está em uso");
        }
        Professor novo = repo.save(professor);
        return new ResponseEntity<>(novo, HttpStatus.CREATED);
    }

    /**
     * Atualiza um professor existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Professor> update(@PathVariable Long id, @Valid @RequestBody Professor professor) {
        Professor existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
        if (!existing.getCpf().equals(professor.getCpf()) && repo.existsByCpf(professor.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já está em uso");
        }
        if (!existing.getRg().equals(professor.getRg()) && repo.existsByRg(professor.getRg())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RG já está em uso");
        }
        if (!existing.getLogin().equals(professor.getLogin()) && repo.existsByLogin(professor.getLogin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login já está em uso");
        }
        existing.setNome(professor.getNome());
        existing.setLogin(professor.getLogin());
        existing.setSenha(professor.getSenha());
        existing.setCpf(professor.getCpf());
        existing.setRg(professor.getRg());
        existing.setDataNascimento(professor.getDataNascimento());
        existing.setEmail(professor.getEmail());
        existing.setTelefone(professor.getTelefone());
        existing.setEndereco(professor.getEndereco());
        Professor atualizado = repo.save(existing);
        return new ResponseEntity<>(atualizado, HttpStatus.OK);
    }

    /**
     * Exclui um professor pelo seu ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}