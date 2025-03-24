package br.com.gestao.academia.administrador.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao.academia.administrador.modelo.Administrador;
import br.com.gestao.academia.administrador.repositorio.AdministradorRepositorio;
import jakarta.validation.Valid;

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
