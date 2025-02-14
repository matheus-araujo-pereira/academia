package br.com.academia.academia.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academia.academia.modelo.Administrador;
import br.com.academia.academia.repositorio.AdministradorRepositorio;

@Service
public class AdministradorServico {

    @Autowired
    private AdministradorRepositorio administradorRepositorio;

    public Administrador save(Administrador admin) {
        return administradorRepositorio.save(admin);
    }

    public List<Administrador> findByName(String nome) {
        return administradorRepositorio.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Administrador> findById(Long id) {
        return administradorRepositorio.findById(id);
    }

    public void deleteById(Long id) {
        administradorRepositorio.deleteById(id);
    }
}