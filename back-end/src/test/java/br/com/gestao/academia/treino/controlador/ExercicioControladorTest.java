package br.com.gestao.academia.treino.controlador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

  @Test
  void testAtualizarExercicioAtualizandoCampos() {
    Exercicio existente = new Exercicio();
    existente.setNome("Original");
    existente.setDescricao("Desc Original");
    existente.setCarga(50.0);
    existente.setRepeticao(10);
    existente.setSeries(3);

    Exercicio novo = new Exercicio();
    novo.setNome("Atualizado");
    novo.setDescricao("Desc Atualizada");
    novo.setCarga(75.0);
    novo.setRepeticao(12);
    novo.setSeries(4);

    when(exercicioRepo.findById(1L)).thenReturn(Optional.of(existente));
    when(exercicioRepo.save(any(Exercicio.class))).thenReturn(novo);

    ResponseEntity<Exercicio> resposta = controlador.update(1L, novo);
    Exercicio atualizado = resposta.getBody();
    assertNotNull(atualizado); // Adicionado para evitar NPE
    assertEquals("Atualizado", atualizado.getNome());
    assertEquals("Desc Atualizada", atualizado.getDescricao());
    assertEquals(75.0, atualizado.getCarga());
    assertEquals(12, atualizado.getRepeticao());
    assertEquals(4, atualizado.getSeries());
  }

  @Test
  void testAtualizarExercicioLambda() {
    Exercicio existente = new Exercicio();
    existente.setNome("Original");
    existente.setDescricao("Desc Original");
    existente.setCarga(50.0);
    existente.setRepeticao(10);
    existente.setSeries(3);

    Exercicio novo = new Exercicio();
    novo.setNome("Novo Nome");
    novo.setDescricao("Nova Descrição");
    novo.setCarga(100.0);
    novo.setRepeticao(15);
    novo.setSeries(5);

    when(exercicioRepo.findById(2L)).thenReturn(Optional.of(existente));
    when(exercicioRepo.save(any(Exercicio.class))).thenReturn(novo);

    ResponseEntity<Exercicio> resposta = controlador.update(2L, novo);
    Exercicio atualizado = resposta.getBody();
    assertNotNull(atualizado); // Garante que a resposta não é nula
    assertEquals("Novo Nome", atualizado.getNome());
    assertEquals("Nova Descrição", atualizado.getDescricao());
    assertEquals(100.0, atualizado.getCarga());
    assertEquals(15, atualizado.getRepeticao());
    assertEquals(5, atualizado.getSeries());
  }

  @Test
  void testUpdateExercicioNaoEncontrado() {
    // Prepara um exercício para atualização
    Exercicio exercicioAtualizar = new Exercicio();
    // Quando o id não existir, findById retorna vazio
    when(exercicioRepo.findById(99L)).thenReturn(Optional.empty());
    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      controlador.update(99L, exercicioAtualizar);
    });
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("Exercício não encontrado", exception.getReason());
  }

  @Test
  void testSearchExercicioNomeVazio() {
    // Para nome nulo ou string vazia, deve chamar findAll()
    List<Exercicio> lista = Arrays.asList(new Exercicio(), new Exercicio());
    when(exercicioRepo.findAll()).thenReturn(lista);

    ResponseEntity<List<Exercicio>> respostaNull = controlador.search(null);
    assertEquals(HttpStatus.OK, respostaNull.getStatusCode());
    assertEquals(lista, respostaNull.getBody());

    ResponseEntity<List<Exercicio>> respostaVazia = controlador.search("  ");
    assertEquals(HttpStatus.OK, respostaVazia.getStatusCode());
    assertEquals(lista, respostaVazia.getBody());
  }

  @Test
  void testSearchExercicioNomeNaoVazio() {
    // Quando um nome não vazio for fornecido, deve chamar
    // findByNomeContainingIgnoreCase
    List<Exercicio> lista = Arrays.asList(new Exercicio(), new Exercicio());
    when(exercicioRepo.findByNomeContainingIgnoreCase("treino")).thenReturn(lista);

    ResponseEntity<List<Exercicio>> resposta = controlador.search("treino");
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertEquals(lista, resposta.getBody());
  }
}
