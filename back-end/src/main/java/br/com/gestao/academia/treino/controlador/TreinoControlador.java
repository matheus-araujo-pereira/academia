package br.com.gestao.academia.treino.controlador;

import java.util.List;
import java.util.stream.Collectors;

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

import br.com.gestao.academia.treino.modelo.Treino;
import br.com.gestao.academia.treino.repositorio.TreinoRepositorio;
import br.com.gestao.academia.professor.repositorio.ProfessorRepositorio;
import br.com.gestao.academia.cliente.repositorio.ClienteRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/treinos")
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem
public class TreinoControlador {

    private final TreinoRepositorio repo;
    private final ProfessorRepositorio professorRepo;
    private final ClienteRepositorio clienteRepo;

    // Ajuste no construtor para receber os novos repositórios
    public TreinoControlador(
            TreinoRepositorio repo,
            ProfessorRepositorio professorRepo,
            ClienteRepositorio clienteRepo) {
        this.repo = repo;
        this.professorRepo = professorRepo;
        this.clienteRepo = clienteRepo;
    }

    @GetMapping
    public List<Treino> findAll(@RequestParam String login) {
        var professorOpt = professorRepo.findByLogin(login);
        if (professorOpt.isPresent()) {
            // Se professor, retorna treinos associados a ele
            return repo.findAll().stream()
                    .filter(t -> t.getProfessor() != null &&
                            t.getProfessor().getId().equals(professorOpt.get().getId()))
                    .collect(Collectors.toList());
        }
        var clienteOpt = clienteRepo.findByLogin(login);
        if (clienteOpt.isPresent()) {
            // Se cliente, retorna somente seus treinos
            return repo.findAll().stream()
                    .filter(t -> t.getCliente() != null &&
                            t.getCliente().getId().equals(clienteOpt.get().getId()))
                    .collect(Collectors.toList());
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
    }

    @PostMapping
    public Treino save(@RequestParam String login, @Valid @RequestBody Treino treino) {
        // Apenas professor pode criar
        var professorOpt = professorRepo.findByLogin(login);
        if (professorOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas professores podem criar treinos");
        }
        return repo.save(treino);
    }

    @PutMapping("/{id}")
    public Treino update(@PathVariable Long id,
            @RequestParam String login,
            @Valid @RequestBody Treino treino) {
        // Apenas professor pode editar
        var professorOpt = professorRepo.findByLogin(login);
        if (professorOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas professores podem editar treinos");
        }
        Treino existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treino não encontrado"));
        existing.setDescricao(treino.getDescricao());
        existing.setDataCriacao(treino.getDataCriacao());
        existing.setCliente(treino.getCliente());
        existing.setProfessor(treino.getProfessor());
        existing.setExercicios(treino.getExercicios());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam String login) {
        // Apenas professor pode excluir
        var professorOpt = professorRepo.findByLogin(login);
        if (professorOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas professores podem excluir treinos");
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
