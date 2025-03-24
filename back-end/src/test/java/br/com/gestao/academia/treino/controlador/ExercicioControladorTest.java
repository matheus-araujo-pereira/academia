package br.com.gestao.academia.treino.controlador;

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

import br.com.gestao.academia.treino.modelo.Exercicio;
import br.com.gestao.academia.treino.repositorio.ExercicioRepositorio;

@ExtendWith(MockitoExtension.class)
class ExercicioControladorTest {

  @Mock
  private ExercicioRepositorio exercicioRepo;

  @InjectMocks
  private ExercicioControlador controlador;

  @BeforeEach
  void setup() {
    // Inicializa o controlador com os mocks
  }

  @Test
  void deveRetornarTodosExercicios() {
    Exercicio exercicio1 = new Exercicio();
    Exercicio exercicio2 = new Exercicio();
    when(exercicioRepo.findAllByOrderByIdAsc()).thenReturn(Arrays.asList(exercicio1, exercicio2));

    ResponseEntity<List<Exercicio>> resposta = controlador.findAll();
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Exercicio> exercicios = resposta.getBody();
    assertNotNull(exercicios);
    assertEquals(2, exercicios.size());
  }

  @Test
  void deveBuscarExerciciosPorNome() {
    Exercicio exercicio1 = new Exercicio();
    Exercicio exercicio2 = new Exercicio();
    when(exercicioRepo.findByNomeContainingIgnoreCase("nome")).thenReturn(Arrays.asList(exercicio1, exercicio2));

    ResponseEntity<List<Exercicio>> resposta = controlador.search("nome");
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Exercicio> exercicios = resposta.getBody();
    assertNotNull(exercicios);
    assertEquals(2, exercicios.size());
  }

  @Test
  void deveCriarExercicio() {
    Exercicio exercicio = new Exercicio();
    when(exercicioRepo.save(any(Exercicio.class))).thenReturn(exercicio);

    ResponseEntity<Exercicio> resposta = controlador.save(exercicio);
    assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveRetornarExercicioPorId() {
    Exercicio exercicio = new Exercicio();
    when(exercicioRepo.findById(1L)).thenReturn(Optional.of(exercicio));

    ResponseEntity<Exercicio> resposta = controlador.getById(1L);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveLancarExcecaoQuandoExercicioNaoEncontrado() {
    when(exercicioRepo.findById(1L)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controlador.getById(1L));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  @Test
  void deveAtualizarExercicio() {
    Exercicio exercicio = new Exercicio();
    when(exercicioRepo.findById(1L)).thenReturn(Optional.of(exercicio));
    when(exercicioRepo.save(any(Exercicio.class))).thenReturn(exercicio);

    ResponseEntity<Exercicio> resposta = controlador.update(1L, exercicio);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveExcluirExercicio() {
    doNothing().when(exercicioRepo).deleteById(1L);

    ResponseEntity<Void> resposta = controlador.delete(1L);
    assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    verify(exercicioRepo, times(1)).deleteById(1L);
  }
}
