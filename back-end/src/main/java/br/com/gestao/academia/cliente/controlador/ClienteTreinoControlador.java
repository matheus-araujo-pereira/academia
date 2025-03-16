package br.com.gestao.academia.cliente.controlador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.gestao.academia.treino.modelo.Treino;
import br.com.gestao.academia.treino.repositorio.TreinoRepositorio;

@RestController
@RequestMapping("/api/clientes")
public class ClienteTreinoControlador {

    private final TreinoRepositorio treinoRepo;

    public ClienteTreinoControlador(TreinoRepositorio treinoRepo) {
        this.treinoRepo = treinoRepo;
    }

    // Consulta os treinos do cliente, com seus exercícios (a associação já está na
    // entidade Treino)
    @GetMapping("/{clienteId}/treinos")
    public List<Treino> getTreinosDoCliente(@PathVariable Long clienteId) {
        List<Treino> treinosDoCliente = treinoRepo.findAll().stream()
                .filter(treino -> treino.getCliente() != null && treino.getCliente().getId().equals(clienteId))
                .collect(Collectors.toList());
        if (treinosDoCliente.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Nenhum treino encontrado para o cliente com id " + clienteId);
        }
        return treinosDoCliente;
    }
}
