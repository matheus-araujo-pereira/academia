package br.com.gestao.academia.plano.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao.academia.plano.modelo.Plano;
import br.com.gestao.academia.plano.repositorio.PlanoRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/planos")
public class PlanoControlador {

    private final PlanoRepositorio repo;

    public PlanoControlador(PlanoRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Plano> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Plano save(@Valid @RequestBody Plano plano) {
        return repo.save(plano);
    }
}
