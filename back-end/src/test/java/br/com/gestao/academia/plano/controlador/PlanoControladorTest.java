package br.com.gestao.academia.plano.controlador;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import br.com.gestao.academia.plano.modelo.Plano;
import br.com.gestao.academia.plano.repositorio.PlanoRepositorio;

@ExtendWith(MockitoExtension.class)
class PlanoControladorTest {

  @Mock
  private PlanoRepositorio planoRepo;

  @InjectMocks
  private PlanoControlador controlador;

  @BeforeEach
  void setup() {
    // Inicializa o controlador com os mocks
  }

  @Test
  void deveRetornarTodosPlanos() {
    Plano plano1 = new Plano();
    Plano plano2 = new Plano();
    when(planoRepo.findAllOrderByValorAsc()).thenReturn(Arrays.asList(plano1, plano2));

    ResponseEntity<List<Plano>> resposta = controlador.findAll();
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Plano> planos = resposta.getBody();
    assertNotNull(planos);
    assertEquals(2, planos.size());
  }

  @Test
  void deveTratarExcecaoNoFindAll() {
    when(planoRepo.findAllOrderByValorAsc()).thenThrow(new RuntimeException("Erro teste"));
    ResponseEntity<List<Plano>> resposta = controlador.findAll();
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resposta.getStatusCode());
  }

  @Test
  void deveBuscarPlanosPorNome_CasoNomeValido() {
    Plano plano1 = new Plano();
    Plano plano2 = new Plano();
    when(planoRepo.findByNomeContainingIgnoreCase("nome")).thenReturn(Arrays.asList(plano1, plano2));

    ResponseEntity<List<Plano>> resposta = controlador.search("nome");
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Plano> planos = resposta.getBody();
    assertNotNull(planos);
    assertEquals(2, planos.size());
  }

  @Test
  void deveBuscarPlanosPorNome_CasoNomeNulo() {
    Plano plano1 = new Plano();
    when(planoRepo.findAllOrderByValorAsc()).thenReturn(Collections.singletonList(plano1));
    ResponseEntity<List<Plano>> resposta = controlador.search(null);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Plano> planos = resposta.getBody();
    assertNotNull(planos);
    assertEquals(1, planos.size());
  }

  @Test
  void deveBuscarPlanosPorNome_CasoNomeVazio() {
    Plano plano1 = new Plano();
    when(planoRepo.findAllOrderByValorAsc()).thenReturn(Collections.singletonList(plano1));
    ResponseEntity<List<Plano>> resposta = controlador.search("");
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Plano> planos = resposta.getBody();
    assertNotNull(planos);
    assertEquals(1, planos.size());
  }

  @Test
  void deveBuscarPlanosPorNome_CasoNomeApenasEspacos() {
    Plano plano1 = new Plano();
    when(planoRepo.findAllOrderByValorAsc()).thenReturn(Collections.singletonList(plano1));
    ResponseEntity<List<Plano>> resposta = controlador.search("    ");
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Plano> planos = resposta.getBody();
    assertNotNull(planos);
    assertEquals(1, planos.size());
  }

  @Test
  void deveCriarPlano() {
    Plano plano = new Plano();
    when(planoRepo.save(any(Plano.class))).thenReturn(plano);

    ResponseEntity<?> resposta = controlador.create(plano);
    assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveTratarExcecaoNoCreate() {
    Plano plano = new Plano();
    when(planoRepo.save(any(Plano.class))).thenThrow(new RuntimeException("Erro create"));
    ResponseEntity<?> resposta = controlador.create(plano);
    Object body = resposta.getBody(); // armazenando o body
    assertNotNull(body);
    assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
    assertTrue(((String) body).contains("Erro ao cadastrar plano"));
  }

  @Test
  void deveRetornarPlanoPorId() {
    Plano plano = new Plano();
    when(planoRepo.findById(1L)).thenReturn(Optional.of(plano));

    ResponseEntity<Plano> resposta = controlador.getById(1L);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveLancarExcecaoQuandoPlanoNaoEncontrado() {
    when(planoRepo.findById(1L)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controlador.getById(1L));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  @Test
  void deveAtualizarPlano() {
    Plano plano = new Plano();
    when(planoRepo.findById(1L)).thenReturn(Optional.of(plano));
    when(planoRepo.save(any(Plano.class))).thenReturn(plano);

    ResponseEntity<?> resposta = controlador.update(1L, plano);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveTratarExcecaoNoUpdateDuranteSave() {
    Plano plano = new Plano();
    when(planoRepo.findById(1L)).thenReturn(Optional.of(plano));
    when(planoRepo.save(any(Plano.class))).thenThrow(new RuntimeException("Erro update"));

    ResponseEntity<?> resposta = controlador.update(1L, plano);
    Object body = resposta.getBody(); // armazenando o body
    assertNotNull(body);
    assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
    assertTrue(((String) body).contains("Erro ao atualizar plano"));
  }

  @Test
  void deveLancarExcecaoQuandoAtualizarPlanoNaoEncontrado() {
    Plano plano = new Plano();
    when(planoRepo.findById(1L)).thenReturn(Optional.empty());
    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      controlador.update(1L, plano);
    });
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  @Test
  void deveExcluirPlano() {
    doNothing().when(planoRepo).deleteById(1L);

    ResponseEntity<?> resposta = controlador.delete(1L);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    verify(planoRepo, times(1)).deleteById(1L);
  }

  @Test
  void deveTratarExcecaoNoDelete() {
    doThrow(new RuntimeException("Erro delete")).when(planoRepo).deleteById(1L);
    ResponseEntity<?> resposta = controlador.delete(1L);
    Object body = resposta.getBody(); // armazenando o body
    assertNotNull(body);
    assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
    assertTrue(((String) body).contains("Erro ao excluir plano"));
  }
}
