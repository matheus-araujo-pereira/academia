package br.com.gestao.academia.cliente.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao.academia.cliente.modelo.Cliente;
import br.com.gestao.academia.cliente.repositorio.ClienteRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteControlador {

    private final ClienteRepositorio repo;

    public ClienteControlador(ClienteRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Cliente> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Cliente save(@Valid @RequestBody Cliente cliente) {
        return repo.save(cliente);
    }
}
