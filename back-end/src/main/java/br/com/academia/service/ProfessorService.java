package br.com.academia.service;

import br.com.academia.model.Funcionario;
import br.com.academia.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario cadastrarProfessor(Funcionario professor) {
        return funcionarioRepository.save(professor);
    }

    public List<Funcionario> consultarProfessores(String nome) {
        return funcionarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Funcionario> atualizarProfessor(Integer id, Funcionario professor) {
        return funcionarioRepository.findById(id).map(existingProfessor -> {
            existingProfessor.setNome(professor.getNome());
            existingProfessor.setCargo(professor.getCargo());
            existingProfessor.setTelefone(professor.getTelefone());
            existingProfessor.setCpf(professor.getCpf());
            existingProfessor.setRg(professor.getRg());
            existingProfessor.setDataNascimento(professor.getDataNascimento());
            existingProfessor.setEmail(professor.getEmail());
            return funcionarioRepository.save(existingProfessor);
        });
    }

    public void excluirProfessor(Integer id) {
        funcionarioRepository.deleteById(id);
    }
}