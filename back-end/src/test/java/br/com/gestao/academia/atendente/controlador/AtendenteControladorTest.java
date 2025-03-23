package br.com.gestao.academia.atendente.controlador;

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

import br.com.gestao.academia.atendente.modelo.Atendente;
import br.com.gestao.academia.atendente.repositorio.AtendenteRepositorio;
import br.com.gestao.academia.endereco.modelo.Endereco;

@ExtendWith(MockitoExtension.class)
class AtendenteControladorTest {

  @Mock
  private AtendenteRepositorio atendenteRepo;

  @InjectMocks
  private AtendenteControlador controlador;

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
  void deveRetornarTodosAtendentes() {
    Atendente atendente1 = new Atendente();
    atendente1.setEndereco(endereco);
    Atendente atendente2 = new Atendente();
    atendente2.setEndereco(endereco);
    when(atendenteRepo.findAllByOrderByNomeAsc()).thenReturn(Arrays.asList(atendente1, atendente2));

    ResponseEntity<List<Atendente>> resposta = controlador.findAll();
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Atendente> atendentes = resposta.getBody();
    assertNotNull(atendentes);
    assertEquals(2, atendentes.size());
  }

  @Test
  void deveCriarAtendente() {
    Atendente atendente = new Atendente();
    atendente.setEndereco(endereco);
    when(atendenteRepo.save(any(Atendente.class))).thenReturn(atendente);

    ResponseEntity<Atendente> resposta = controlador.create(atendente);
    assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveRetornarAtendentePorId() {
    Atendente atendente = new Atendente();
    atendente.setEndereco(endereco);
    when(atendenteRepo.findById(1L)).thenReturn(Optional.of(atendente));

    ResponseEntity<Atendente> resposta = controlador.getById(1L);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveLancarExcecaoQuandoAtendenteNaoEncontrado() {
    when(atendenteRepo.findById(1L)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controlador.getById(1L));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  @Test
  void deveAtualizarAtendente() {
    Atendente atendente = new Atendente();
    atendente.setCpf("12345678901");
    atendente.setRg("123456789");
    atendente.setLogin("login");
    atendente.setEndereco(endereco);
    when(atendenteRepo.findById(1L)).thenReturn(Optional.of(atendente));
    when(atendenteRepo.save(any(Atendente.class))).thenReturn(atendente);

    ResponseEntity<Atendente> resposta = controlador.update(1L, atendente);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveExcluirAtendente() {
    doNothing().when(atendenteRepo).deleteById(1L);

    ResponseEntity<Void> resposta = controlador.delete(1L);
    assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    verify(atendenteRepo, times(1)).deleteById(1L);
  }
}
