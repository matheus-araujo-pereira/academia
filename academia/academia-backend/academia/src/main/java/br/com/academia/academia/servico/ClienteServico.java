package br.com.academia.academia.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academia.academia.modelo.Cliente;
import br.com.academia.academia.repositorio.ClienteRepositorio;

@Service
public class ClienteServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public Cliente save(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    public List<Cliente> findByCpf(String cpf) {
        return clienteRepositorio.findByCpfContainingIgnoreCase(cpf);
    }

    public List<Cliente> findByName(String nome) {
        return clienteRepositorio.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepositorio.findById(id);
    }

    public void deleteById(Long id) {
        clienteRepositorio.deleteById(id);
    }
}
