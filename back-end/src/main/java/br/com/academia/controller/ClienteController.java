package br.com.academia.controller;

import br.com.academia.model.Cliente;
import br.com.academia.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente savedCliente = clienteService.saveCliente(cliente);
        return ResponseEntity.ok(savedCliente);
    }

    @GetMapping
    public ResponseEntity<Cliente> getClienteByCpf(@RequestParam String cpf) {
        Optional<Cliente> cliente = clienteService.findClienteByCpf(cpf);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Integer id, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> updatedCliente = clienteService.updateCliente(id, clienteDetails);
        return updatedCliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        Optional<Cliente> cliente = clienteService.getClienteById(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}