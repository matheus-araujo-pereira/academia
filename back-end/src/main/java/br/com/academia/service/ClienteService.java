package br.com.academia.service;

import br.com.academia.model.Cliente;
import br.com.academia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente saveCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> findClienteByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public Optional<Cliente> updateCliente(Integer id, Cliente clienteDetails) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(clienteDetails.getNome());
            cliente.setCpf(clienteDetails.getCpf());
            cliente.setRg(clienteDetails.getRg());
            cliente.setDataNascimento(clienteDetails.getDataNascimento());
            cliente.setEmail(clienteDetails.getEmail());
            cliente.setTelefone(clienteDetails.getTelefone());
            return clienteRepository.save(cliente);
        });
    }

    public void deleteCliente(Integer id) {
        clienteRepository.deleteById(id);
    }

    public Optional<Cliente> getClienteById(Integer id) {
        return clienteRepository.findById(id);
    }
}