package br.com.gestao.academia.treino.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao.academia.treino.modelo.Treino;
import br.com.gestao.academia.treino.repositorio.TreinoRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/treinos")
public class TreinoControlador {

    private final TreinoRepositorio repo;

    public TreinoControlador(TreinoRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Treino> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Treino save(@Valid @RequestBody Treino treino) {
        // Aqui, você pode (na lógica de serviço ou controller) limitar o número de
        // treinos para um cliente a 7.
        return repo.save(treino);
    }
}
