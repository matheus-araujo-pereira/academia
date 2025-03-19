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
        try {
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

        if (n.isEmpty() && c.isEmpty()) {
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        } else if (!n.isEmpty() && !c.isEmpty()) {
            return new ResponseEntity<>(repo.findByNomeContainingIgnoreCaseAndCpfContainingIgnoreCase(n, c),
                    HttpStatus.OK);
        } else if (!n.isEmpty()) {
            return new ResponseEntity<>(repo.findByNomeContainingIgnoreCase(n), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(repo.findByCpfContainingIgnoreCase(c), HttpStatus.OK);
        }
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
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente) {
        try {
            Plano plano = planRepo.findById(cliente.getPlano().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));
            cliente.setPlano(plano);
            Cliente novo = repo.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Cliente cadastrado com sucesso! ID: " + novo.getId());
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um cliente pelo ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        try {
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

            Plano plano = planRepo.findById(cliente.getPlano().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));
            existing.setPlano(plano);

            existing.setEndereco(cliente.getEndereco());
            repo.save(existing);
            return ResponseEntity.ok("Cliente atualizado com sucesso!");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    /**
     * Exclui um cliente pelo ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            repo.deleteById(id);
            return ResponseEntity.ok("Cliente excluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao excluir cliente: " + e.getMessage());
        }
    }
}
