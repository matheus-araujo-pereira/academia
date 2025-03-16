package br.com.gestao.academia.atendente.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao.academia.atendente.modelo.Atendente;
import br.com.gestao.academia.atendente.repositorio.AtendenteRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/atendentes")
public class AtendenteControlador {

    private final AtendenteRepositorio repo;

    public AtendenteControlador(AtendenteRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Atendente> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Atendente save(@Valid @RequestBody Atendente atendente) {
        return repo.save(atendente);
    }
}
