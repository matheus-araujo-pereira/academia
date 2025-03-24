package br.com.gestao.academia.atividade.controlador;

import static org.mockito.ArgumentMatchers.any;
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

import br.com.gestao.academia.atividade.modelo.Atividade;
import br.com.gestao.academia.atividade.repositorio.AtividadeRepositorio;

@ExtendWith(MockitoExtension.class)
class AtividadeControladorTest {

  @Mock
  private AtividadeRepositorio atividadeRepo;

  @InjectMocks
  private AtividadeControlador controlador;

  @BeforeEach
  void setup() {
    // Inicializa o controlador com os mocks
  }

  @Test
  void deveRetornarTodasAtividades() {
    Atividade atividade1 = new Atividade();
    Atividade atividade2 = new Atividade();
    when(atividadeRepo.findAll()).thenReturn(Arrays.asList(atividade1, atividade2));

    ResponseEntity<List<Atividade>> resposta = controlador.findAll();
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Atividade> atividades = resposta.getBody();
    assertNotNull(atividades);
    assertEquals(2, atividades.size());
  }

  @Test
  void deveCriarAtividade() {
    Atividade atividade = new Atividade();
    when(atividadeRepo.save(any(Atividade.class))).thenReturn(atividade);

    ResponseEntity<Atividade> resposta = controlador.save(atividade);
    assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveRetornarAtividadePorId() {
    Atividade atividade = new Atividade();
    when(atividadeRepo.findById(1L)).thenReturn(Optional.of(atividade));

    ResponseEntity<Atividade> resposta = controlador.getById(1L);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveLancarExcecaoQuandoAtividadeNaoEncontrada() {
    when(atividadeRepo.findById(1L)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controlador.getById(1L));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  @Test
  void deveAtualizarAtividade() {
    Atividade atividade = new Atividade();
    when(atividadeRepo.findById(1L)).thenReturn(Optional.of(atividade));
    when(atividadeRepo.save(any(Atividade.class))).thenReturn(atividade);

    ResponseEntity<Atividade> resposta = controlador.update(1L, atividade);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveLancarExcecaoAoAtualizarAtividadeNaoEncontrada() {
    Atividade atividade = new Atividade();
    when(atividadeRepo.findById(1L)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> controlador.update(1L, atividade));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("Atividade não encontrada", exception.getReason());
  }

  @Test
  void deveExcluirAtividade() {
    doNothing().when(atividadeRepo).deleteById(1L);

    ResponseEntity<Void> resposta = controlador.delete(1L);
    assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    verify(atividadeRepo, times(1)).deleteById(1L);
  }
}
