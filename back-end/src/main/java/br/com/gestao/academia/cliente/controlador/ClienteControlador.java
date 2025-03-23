package br.com.gestao.academia.cliente.controlador;

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

import br.com.gestao.academia.cliente.modelo.Cliente;
import br.com.gestao.academia.cliente.repositorio.ClienteRepositorio;
import br.com.gestao.academia.plano.modelo.Plano;
import br.com.gestao.academia.plano.repositorio.PlanoRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*") // Para permitir CORS
public class ClienteControlador {

    private final ClienteRepositorio repo;
    private final PlanoRepositorio planRepo;

    public ClienteControlador(ClienteRepositorio repo, PlanoRepositorio planRepo) {
        this.repo = repo;
        this.planRepo = planRepo;
    }

    /**
     * Lista todos os clientes ou retorna erro em caso de exceção.
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> findAll() {
        return new ResponseEntity<>(repo.findAllByOrderByNomeAsc(), HttpStatus.OK);
    }

    /**
     * Filtra clientes por nome e/ou CPF, retornando lista.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Cliente>> search(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf) {
        String n = (nome == null ? "" : nome.trim());
        String c = (cpf == null ? "" : cpf.trim());

        List<Cliente> result;
        if (n.isEmpty() && c.isEmpty()) {
            result = repo.findAllByOrderByNomeAsc();
        } else if (!n.isEmpty() && !c.isEmpty()) {
            result = repo.findByNomeContainingIgnoreCaseAndCpfContainingIgnoreCase(n, c);
        } else if (!n.isEmpty()) {
            result = repo.findByNomeContainingIgnoreCase(n);
        } else {
            result = repo.findByCpfContainingIgnoreCase(c);
        }
        // Ordena a lista de clientes por nome (ordem alfabética)
        result.sort((a, b) -> a.getNome().compareToIgnoreCase(b.getNome()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retorna um cliente específico por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        Cliente cliente = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    /**
     * Cria um novo cliente, associando-o a um plano (obrigatório).
     */
    @PostMapping
    public ResponseEntity<Cliente> create(@Valid @RequestBody Cliente cliente) {
        if (repo.existsByCpf(cliente.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já está em uso");
        }
        if (repo.existsByRg(cliente.getRg())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RG já está em uso");
        }
        if (repo.existsByLogin(cliente.getLogin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login já está em uso");
        }
        Plano plano = planRepo.findById(cliente.getPlano().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));
        cliente.setPlano(plano);
        Cliente novo = repo.save(cliente);
        return new ResponseEntity<>(novo, HttpStatus.CREATED);
    }

    /**
     * Atualiza os dados de um cliente pelo ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        Cliente existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        if (!existing.getCpf().equals(cliente.getCpf()) && repo.existsByCpf(cliente.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já está em uso");
        }
        if (!existing.getRg().equals(cliente.getRg()) && repo.existsByRg(cliente.getRg())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RG já está em uso");
        }
        if (!existing.getLogin().equals(cliente.getLogin()) && repo.existsByLogin(cliente.getLogin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login já está em uso");
        }
        existing.setNome(cliente.getNome());
        existing.setLogin(cliente.getLogin());
        existing.setSenha(cliente.getSenha());
        existing.setCpf(cliente.getCpf());
        existing.setRg(cliente.getRg());
        existing.setDataNascimento(cliente.getDataNascimento());
        existing.setEmail(cliente.getEmail());
        existing.setTelefone(cliente.getTelefone());
        existing.setEndereco(cliente.getEndereco());
        Plano plano = planRepo.findById(cliente.getPlano().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));
        existing.setPlano(plano);
        Cliente atualizado = repo.save(existing);
        return new ResponseEntity<>(atualizado, HttpStatus.OK);
    }

    /**
     * Exclui um cliente pelo ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
