package br.com.gestao.academia.login.controlador;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.gestao.academia.administrador.modelo.Administrador;
import br.com.gestao.academia.administrador.repositorio.AdministradorRepositorio;
import br.com.gestao.academia.atendente.modelo.Atendente;
import br.com.gestao.academia.atendente.repositorio.AtendenteRepositorio;
import br.com.gestao.academia.cliente.modelo.Cliente;
import br.com.gestao.academia.cliente.repositorio.ClienteRepositorio;
import br.com.gestao.academia.login.dto.LoginRequest;
import br.com.gestao.academia.login.dto.LoginResponse;
import br.com.gestao.academia.professor.modelo.Professor;
import br.com.gestao.academia.professor.repositorio.ProfessorRepositorio;
import jakarta.validation.Valid;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "*") // Permite requisições de qualquer origem
@RestController
@RequestMapping("/api")
public class LoginControlador {
    private final AdministradorRepositorio adminRepo;
    private final AtendenteRepositorio atendenteRepo;
    private final ProfessorRepositorio professorRepo;
    private final ClienteRepositorio clienteRepo;

    public LoginControlador(AdministradorRepositorio adminRepo,
            AtendenteRepositorio atendenteRepo,
            ProfessorRepositorio professorRepo,
            ClienteRepositorio clienteRepo) {
        this.adminRepo = adminRepo;
        this.atendenteRepo = atendenteRepo;
        this.professorRepo = professorRepo;
        this.clienteRepo = clienteRepo;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        // Verifica Administrador
        Optional<Administrador> adminOpt = adminRepo.findByLogin(request.getLogin());
        if (adminOpt.isPresent() && adminOpt.get().getSenha().equals(request.getSenha())) {
            session.setAttribute("userLogin", request.getLogin());
            return new LoginResponse("Login realizado com sucesso", "administrador");
        }

        // Verifica Atendente
        Optional<Atendente> atendenteOpt = atendenteRepo.findByLogin(request.getLogin());
        if (atendenteOpt.isPresent() && atendenteOpt.get().getSenha().equals(request.getSenha())) {
            session.setAttribute("userLogin", request.getLogin());
            return new LoginResponse("Login realizado com sucesso", "atendente");
        }

        // Verifica Professor
        Optional<Professor> professorOpt = professorRepo.findByLogin(request.getLogin());
        if (professorOpt.isPresent() && professorOpt.get().getSenha().equals(request.getSenha())) {
            session.setAttribute("userLogin", request.getLogin());
            return new LoginResponse("Login realizado com sucesso", "professor");
        }

        // Verifica Cliente
        Optional<Cliente> clienteOpt = clienteRepo.findByLogin(request.getLogin());
        if (clienteOpt.isPresent() && clienteOpt.get().getSenha().equals(request.getSenha())) {
            session.setAttribute("userLogin", request.getLogin());
            return new LoginResponse("Login realizado com sucesso", "cliente");
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
    }
}