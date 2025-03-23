package br.com.gestao.academia.professor.controlador;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import br.com.gestao.academia.professor.modelo.Professor;
import br.com.gestao.academia.professor.repositorio.ProfessorRepositorio;
import br.com.gestao.academia.endereco.modelo.Endereco;

@ExtendWith(MockitoExtension.class)
class ProfessorControladorTest {

  @Mock
  private ProfessorRepositorio professorRepo;

  @InjectMocks
  private ProfessorControlador controlador;

  private Endereco endereco;

  @BeforeEach
  void setup() {
    endereco = new Endereco();
    endereco.setId(1L);
    endereco.setEstado("SP");
    endereco.setCidade("São Paulo");
    endereco.setBairro("Centro");
    endereco.setLogradouro("Rua A");
    endereco.setNumero("123");
    endereco.setCep("12345-678");
  }

  @Test
  void deveRetornarTodosProfessores() {
    Professor professor1 = new Professor();
    professor1.setEndereco(endereco);
    Professor professor2 = new Professor();
    professor2.setEndereco(endereco);
    when(professorRepo.findAllByOrderByNomeAsc()).thenReturn(Arrays.asList(professor1, professor2));

    ResponseEntity<List<Professor>> resposta = controlador.findAll();
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Professor> professores = resposta.getBody();
    assertNotNull(professores);
    assertEquals(2, professores.size());
  }

  @Test
  void deveCriarProfessor() {
    Professor professor = new Professor();
    professor.setEndereco(endereco);
    when(professorRepo.save(any(Professor.class))).thenReturn(professor);

    ResponseEntity<Professor> resposta = controlador.save(professor);
    assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveRetornarProfessorPorId() {
    Professor professor = new Professor();
    professor.setEndereco(endereco);
    when(professorRepo.findById(1L)).thenReturn(Optional.of(professor));

    ResponseEntity<Professor> resposta = controlador.getById(1L);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveLancarExcecaoQuandoProfessorNaoEncontrado() {
    when(professorRepo.findById(1L)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controlador.getById(1L));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  @Test
  void deveAtualizarProfessor() {
    Professor professor = new Professor();
    professor.setCpf("12345678901");
    professor.setRg("123456789");
    professor.setLogin("login");
    professor.setEndereco(endereco);
    when(professorRepo.findById(1L)).thenReturn(Optional.of(professor));
    when(professorRepo.save(any(Professor.class))).thenReturn(professor);

    ResponseEntity<Professor> resposta = controlador.update(1L, professor);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveExcluirProfessor() {
    doNothing().when(professorRepo).deleteById(1L);

    ResponseEntity<Void> resposta = controlador.delete(1L);
    assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    verify(professorRepo, times(1)).deleteById(1L);
  }
}
