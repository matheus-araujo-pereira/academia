package br.com.gestao.academia.usuario.controlador;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import br.com.gestao.academia.administrador.modelo.Administrador;
import br.com.gestao.academia.administrador.repositorio.AdministradorRepositorio;
import br.com.gestao.academia.atendente.modelo.Atendente;
import br.com.gestao.academia.atendente.repositorio.AtendenteRepositorio;
import br.com.gestao.academia.professor.modelo.Professor;
import br.com.gestao.academia.professor.repositorio.ProfessorRepositorio;
import br.com.gestao.academia.cliente.modelo.Cliente;
import br.com.gestao.academia.cliente.repositorio.ClienteRepositorio;
import br.com.gestao.academia.treino.repositorio.TreinoRepositorio;

@ExtendWith(MockitoExtension.class)
class DadosPessoaisControladorTest {

  @Mock
  private AdministradorRepositorio adminRepo;
  @Mock
  private AtendenteRepositorio atendenteRepo;
  @Mock
  private ProfessorRepositorio professorRepo;
  @Mock
  private ClienteRepositorio clienteRepo;
  @Mock
  private TreinoRepositorio treinoRepo;

  @InjectMocks
  private DadosPessoaisControlador controlador;

  @BeforeEach
  void setup() {
    // Inicializa o controlador com os mocks
  }

  @Test
  void deveRetornarDadosAdministrador() {
    Administrador admin = new Administrador();
    admin.setNome("Admin");
    when(adminRepo.findByLogin("adminLogin")).thenReturn(Optional.of(admin));

    ResponseEntity<Map<String, Object>> resposta = controlador.getDadosPessoais("adminLogin");
    Map<String, Object> body = resposta.getBody();
    assertNotNull(body);
    assertEquals("administrador", body.get("tipoUsuario"));
  }

  @Test
  void deveRetornarDadosAtendente() {
    Atendente atendente = new Atendente();
    atendente.setNome("Atendente");
    when(atendenteRepo.findByLogin("attLogin")).thenReturn(Optional.of(atendente));

    ResponseEntity<Map<String, Object>> resposta = controlador.getDadosPessoais("attLogin");
    Map<String, Object> body = resposta.getBody();
    assertNotNull(body);
    assertEquals("atendente", body.get("tipoUsuario"));
  }

  @Test
  void deveRetornarDadosProfessor() {
    Professor professor = new Professor();
    professor.setNome("Professor");
    when(professorRepo.findByLogin("profLogin")).thenReturn(Optional.of(professor));

    ResponseEntity<Map<String, Object>> resposta = controlador.getDadosPessoais("profLogin");
    Map<String, Object> body = resposta.getBody();
    assertNotNull(body);
    assertEquals("professor", body.get("tipoUsuario"));
  }

  @Test
  void deveRetornarDadosCliente() {
    Cliente cliente = new Cliente();
    cliente.setNome("Cliente");
    when(clienteRepo.findByLogin("cliLogin")).thenReturn(Optional.of(cliente));

    ResponseEntity<Map<String, Object>> resposta = controlador.getDadosPessoais("cliLogin");
    Map<String, Object> body = resposta.getBody();
    assertNotNull(body);
    assertEquals("cliente", body.get("tipoUsuario"));
  }

  @Test
  void deveRetornarNotFoundSeNaoExistirUsuario() {
    ResponseEntity<Map<String, Object>> resposta = controlador.getDadosPessoais("inexistente");
    assertTrue(resposta.getStatusCode().is4xxClientError());
  }
}