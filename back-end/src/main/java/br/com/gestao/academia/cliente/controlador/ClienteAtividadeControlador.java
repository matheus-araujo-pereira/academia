package br.com.gestao.academia.cliente.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.gestao.academia.atividade.modelo.Atividade;
import br.com.gestao.academia.atividade.repositorio.AtividadeRepositorio;
import br.com.gestao.academia.cliente.modelo.Cliente;
import br.com.gestao.academia.cliente.repositorio.ClienteRepositorio;

@RestController
@RequestMapping("/api/clientes")
public class ClienteAtividadeControlador {

    private final ClienteRepositorio clienteRepo;
    private final AtividadeRepositorio atividadeRepo;

    public ClienteAtividadeControlador(ClienteRepositorio clienteRepo, AtividadeRepositorio atividadeRepo) {
        this.clienteRepo = clienteRepo;
        this.atividadeRepo = atividadeRepo;
    }

    // Consulta geral das atividades disponíveis
    @GetMapping("/atividades")
    public List<Atividade> getAtividades() {
        return atividadeRepo.findAll();
    }

    // Inscrição do cliente em uma atividade
    @PostMapping("/{clienteId}/atividades/{atividadeId}/inscrever")
    public ResponseEntity<Atividade> inscreverAtividade(@PathVariable Long clienteId, @PathVariable Long atividadeId) {
        Cliente cliente = clienteRepo.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        Atividade atividade = atividadeRepo.findById(atividadeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade não encontrada"));

        // Se o cliente ainda não estiver inscrito, inscreva-o
        if (atividade.getClientes() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "A lista de clientes não foi inicializada na atividade");
        }
        if (!atividade.getClientes().contains(cliente)) {
            atividade.getClientes().add(cliente);
            atividadeRepo.save(atividade);
        }
        return ResponseEntity.ok(atividade);
    }

    // Consulta das atividades em que o cliente já está inscrito
    @GetMapping("/{clienteId}/minhas-atividades")
    public List<Atividade> getMinhasAtividades(@PathVariable Long clienteId) {
        List<Atividade> atividades = atividadeRepo.findAll();
        atividades.removeIf(atividade -> atividade.getClientes() == null ||
                atividade.getClientes().stream().noneMatch(c -> c.getId().equals(clienteId)));
        return atividades;
    }
}
