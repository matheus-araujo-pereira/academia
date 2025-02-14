package br.com.academia.academia.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.academia.academia.modelo.Cliente;
import br.com.academia.academia.servico.ClienteServico;

@RestController
@RequestMapping("/api/clientes")
public class ClienteControlador {

    @Autowired
    private ClienteServico clienteServico;

    // Cadastrar Cliente
    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        Cliente saved = clienteServico.save(cliente);
        return ResponseEntity.ok(saved);
    }

    // Consultar Cliente por CPF ou Nome
    @GetMapping("/cpf")
    public ResponseEntity<List<Cliente>> consultarClientePorCpf(@RequestParam(value = "cpf") String cpf) {
        List<Cliente> lista = clienteServico.findByCpf(cpf);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Cliente>> consultarClientePorNome(
            @RequestParam(value = "nome", defaultValue = "") String nome) {
        List<Cliente> lista = clienteServico.findByName(nome);
        return ResponseEntity.ok(lista);
    }

    // Atualizar Cliente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Optional<Cliente> existente = clienteServico.findById(id);
        if (existente.isPresent()) {
            cliente.setId(id);
            Cliente atualizado = clienteServico.save(cliente);
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Excluir Cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        Optional<Cliente> existente = clienteServico.findById(id);
        if (existente.isPresent()) {
            clienteServico.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
