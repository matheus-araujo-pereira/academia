package br.com.gestao.academia.plano.controlador;

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
  void deveBuscarPlanosPorNome() {
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
  void deveCriarPlano() {
    Plano plano = new Plano();
    when(planoRepo.save(any(Plano.class))).thenReturn(plano);

    ResponseEntity<?> resposta = controlador.create(plano);
    assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
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
  void deveExcluirPlano() {
    doNothing().when(planoRepo).deleteById(1L);

    ResponseEntity<?> resposta = controlador.delete(1L);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    verify(planoRepo, times(1)).deleteById(1L);
  }
}
