package br.com.gestao.academia.usuario.controlador;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao.academia.administrador.modelo.Administrador;
import br.com.gestao.academia.administrador.repositorio.AdministradorRepositorio;
import br.com.gestao.academia.atendente.modelo.Atendente;
import br.com.gestao.academia.atendente.repositorio.AtendenteRepositorio;
import br.com.gestao.academia.cliente.modelo.Cliente;
import br.com.gestao.academia.cliente.repositorio.ClienteRepositorio;
import br.com.gestao.academia.professor.modelo.Professor;
import br.com.gestao.academia.professor.repositorio.ProfessorRepositorio;

@RestController
@RequestMapping("/api/dados-pessoais")
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem
public class DadosPessoaisControlador {

    private final AdministradorRepositorio adminRepo;
    private final AtendenteRepositorio atendenteRepo;
    private final ProfessorRepositorio professorRepo;
    private final ClienteRepositorio clienteRepo;

    public DadosPessoaisControlador(AdministradorRepositorio adminRepo,
            AtendenteRepositorio atendenteRepo,
            ProfessorRepositorio professorRepo,
            ClienteRepositorio clienteRepo) {
        this.adminRepo = adminRepo;
        this.atendenteRepo = atendenteRepo;
        this.professorRepo = professorRepo;
        this.clienteRepo = clienteRepo;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDadosPessoais(@RequestParam String login) {
        Map<String, Object> dados = new HashMap<>();

        // Verifica Administrador
        Optional<Administrador> adminOpt = adminRepo.findByLogin(login);
        if (adminOpt.isPresent()) {
            Administrador admin = adminOpt.get();
            dados.put("nome", admin.getNome());
            dados.put("email", admin.getEmail());
            dados.put("tipoUsuario", "administrador");
            return new ResponseEntity<>(dados, HttpStatus.OK);
        }

        // Verifica Atendente
        Optional<Atendente> atendenteOpt = atendenteRepo.findByLogin(login);
        if (atendenteOpt.isPresent()) {
            Atendente atendente = atendenteOpt.get();
            dados.put("nome", atendente.getNome());
            dados.put("email", atendente.getEmail());
            dados.put("tipoUsuario", "atendente");
            return new ResponseEntity<>(dados, HttpStatus.OK);
        }

        // Verifica Professor
        Optional<Professor> professorOpt = professorRepo.findByLogin(login);
        if (professorOpt.isPresent()) {
            Professor professor = professorOpt.get();
            dados.put("nome", professor.getNome());
            dados.put("email", professor.getEmail());
            dados.put("tipoUsuario", "professor");
            return new ResponseEntity<>(dados, HttpStatus.OK);
        }

        // Verifica Cliente
        Optional<Cliente> clienteOpt = clienteRepo.findByLogin(login);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            dados.put("nome", cliente.getNome());
            dados.put("email", cliente.getEmail());
            dados.put("tipoUsuario", "cliente");
            return new ResponseEntity<>(dados, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
