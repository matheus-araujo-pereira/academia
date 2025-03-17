package br.com.gestao.academia.endereco.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao.academia.endereco.modelo.Endereco;
import br.com.gestao.academia.endereco.repositorio.EnderecoRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/enderecos")
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem
public class EnderecoControlador {

    private final EnderecoRepositorio repositorio;

    public EnderecoControlador(EnderecoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Endereco> findAll() {
        return repositorio.findAll();
    }

    @PostMapping
    public Endereco save(@Valid @RequestBody Endereco endereco) {
        return repositorio.save(endereco);
    }
}
