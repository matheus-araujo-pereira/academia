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

    @GetMapping("/{id}")
    public Administrador findById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado para o ID: " + id));
    }

    @PutMapping("/{id}")
    public Administrador update(@PathVariable Long id, @Valid @RequestBody Administrador admin) {
        Administrador admExistente = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado para atualização."));
        admExistente.setNome(admin.getNome());
        admExistente.setLogin(admin.getLogin());
        admExistente.setSenha(admin.getSenha());
        return repo.save(admExistente);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Administrador adm = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado para exclusão."));
        repo.delete(adm);
    }
}
