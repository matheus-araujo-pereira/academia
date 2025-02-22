package br.com.academia.service;

import br.com.academia.model.Funcionario;
import br.com.academia.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtendenteService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario cadastrarAtendente(Funcionario atendente) {
        return funcionarioRepository.save(atendente);
    }

    public List<Funcionario> consultarAtendentes(String nome) {
        return funcionarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Funcionario> atualizarAtendente(Integer id, Funcionario atendente) {
        return funcionarioRepository.findById(id).map(existingAtendente -> {
            existingAtendente.setNome(atendente.getNome());
            existingAtendente.setCargo(atendente.getCargo());
            existingAtendente.setTelefone(atendente.getTelefone());
            existingAtendente.setCpf(atendente.getCpf());
            existingAtendente.setRg(atendente.getRg());
            existingAtendente.setDataNascimento(atendente.getDataNascimento());
            existingAtendente.setEmail(atendente.getEmail());
            return funcionarioRepository.save(existingAtendente);
        });
    }

    public void excluirAtendente(Integer id) {
        funcionarioRepository.deleteById(id);
    }
}