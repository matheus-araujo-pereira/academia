package br.com.gestao.academia.atividade.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao.academia.atividade.modelo.Atividade;
import br.com.gestao.academia.atividade.repositorio.AtividadeRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeControlador {

    private final AtividadeRepositorio repo;

    public AtividadeControlador(AtividadeRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Atividade> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Atividade save(@Valid @RequestBody Atividade atividade) {
        return repo.save(atividade);
    }
}
