package br.com.gestao.academia.login.controlador;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.gestao.academia.administrador.modelo.Administrador;
import br.com.gestao.academia.administrador.repositorio.AdministradorRepositorio;
import br.com.gestao.academia.atendente.modelo.Atendente;
import br.com.gestao.academia.atendente.repositorio.AtendenteRepositorio;
import br.com.gestao.academia.professor.modelo.Professor;
import br.com.gestao.academia.professor.repositorio.ProfessorRepositorio;
import br.com.gestao.academia.cliente.modelo.Cliente;
import br.com.gestao.academia.cliente.repositorio.ClienteRepositorio;
import br.com.gestao.academia.login.dto.LoginRequest;
import br.com.gestao.academia.login.dto.LoginResponse;
import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
class LoginControladorTest {

  @Mock
  private AdministradorRepositorio adminRepo;
  @Mock
  private AtendenteRepositorio atendenteRepo;
  @Mock
  private ProfessorRepositorio professorRepo;
  @Mock
  private ClienteRepositorio clienteRepo;
  @Mock
  private HttpSession session;

  @InjectMocks
  private LoginControlador controlador;

  @BeforeEach
  void init() {
    // Inicializa o controlador com os mocks
  }

  @Test
  void deveRetornarAdministradorQuandoLoginValido() {
    Administrador admin = new Administrador();
    admin.setSenha("senhaAdmin");
    when(adminRepo.findByLogin("adminLogin")).thenReturn(Optional.of(admin));

    LoginRequest request = new LoginRequest();
    request.setLogin("adminLogin");
    request.setSenha("senhaAdmin");
    LoginResponse response = controlador.login(request, session);

    assertEquals("administrador", response.getTipoUsuario());
  }

  @Test
  void deveRetornarAtendenteQuandoLoginValido() {
    Atendente atendente = new Atendente();
    atendente.setSenha("senhaAtendente");
    when(atendenteRepo.findByLogin("attLogin")).thenReturn(Optional.of(atendente));

    LoginRequest request = new LoginRequest();
    request.setLogin("attLogin");
    request.setSenha("senhaAtendente");
    LoginResponse response = controlador.login(request, session);
    assertEquals("atendente", response.getTipoUsuario());
  }

  @Test
  void deveRetornarProfessorQuandoLoginValido() {
    Professor prof = new Professor();
    prof.setSenha("senhaProfessor");
    when(professorRepo.findByLogin("profLogin")).thenReturn(Optional.of(prof));

    LoginRequest request = new LoginRequest();
    request.setLogin("profLogin");
    request.setSenha("senhaProfessor");
    LoginResponse response = controlador.login(request, session);
    assertEquals("professor", response.getTipoUsuario());
  }

  @Test
  void deveRetornarClienteQuandoLoginValido() {
    Cliente cliente = new Cliente();
    cliente.setSenha("senhaCliente");
    when(clienteRepo.findByLogin("cliLogin")).thenReturn(Optional.of(cliente));

    LoginRequest request = new LoginRequest();
    request.setLogin("cliLogin");
    request.setSenha("senhaCliente");
    LoginResponse response = controlador.login(request, session);
    assertEquals("cliente", response.getTipoUsuario());
  }

  @Test
  void deveLancarExcecaoQuandoLoginInvalido() {
    LoginRequest request = new LoginRequest();
    request.setLogin("naoExiste");
    request.setSenha("senha");
    assertThrows(Exception.class, () -> controlador.login(request, session));
  }
}