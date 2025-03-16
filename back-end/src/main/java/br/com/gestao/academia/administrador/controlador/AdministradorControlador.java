package br.com.gestao.academia.administrador.controlador;

import org.springframework.web.bind.annotation.*;

import br.com.gestao.academia.administrador.modelo.Administrador;
import br.com.gestao.academia.administrador.repositorio.AdministradorRepositorio;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorControlador {
    private final AdministradorRepositorio repo;

    public AdministradorControlador(AdministradorRepositorio repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Administrador> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Administrador save(@Valid @RequestBody Administrador admin) {
        return repo.save(admin);
    }
    // ...
}
