package br.com.gestao.academia.treino.controlador;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import br.com.gestao.academia.treino.modelo.Treino;
import br.com.gestao.academia.treino.repositorio.TreinoRepositorio;
import br.com.gestao.academia.cliente.modelo.Cliente;
import br.com.gestao.academia.endereco.modelo.Endereco;
import br.com.gestao.academia.plano.modelo.Plano;
import br.com.gestao.academia.administrador.modelo.Administrador;

@ExtendWith(MockitoExtension.class)
class TreinoControladorTest {

  @Mock
  private TreinoRepositorio treinoRepo;

  @InjectMocks
  private TreinoControlador controlador;

  private Cliente cliente;
  private Endereco endereco;
  private Plano plano;
  private Administrador administrador;

  @BeforeEach
  void setup() {
    administrador = new Administrador();
    administrador.setNome("Admin Teste");
    administrador.setLogin("admin");
    administrador.setSenha("senha123");
    administrador.setCpf("12345678901");
    administrador.setRg("123456789");
    administrador.setDataNascimento(LocalDate.of(1980, 1, 1));
    administrador.setEmail("admin@teste.com");
    administrador.setTelefone("+5511999999999");

    endereco = new Endereco();
    endereco.setEstado("SP");
    endereco.setCidade("São Paulo");
    endereco.setBairro("Centro");
    endereco.setLogradouro("Rua A");
    endereco.setNumero("123");
    endereco.setCep("12345-678");
    endereco.setAdministrador(administrador);

    plano = new Plano();
    plano.setNome("Plano Básico");
    plano.setValor(new BigDecimal("99.99"));
    plano.setAdministrador(administrador);

    cliente = new Cliente();
    cliente.setNome("Cliente Teste");
    cliente.setLogin("cliente");
    cliente.setSenha("senha123");
    cliente.setCpf("12345678901");
    cliente.setRg("123456789");
    cliente.setDataNascimento(LocalDate.of(1990, 1, 1));
    cliente.setEmail("cliente@teste.com");
    cliente.setTelefone("+5511999999999");
    cliente.setEndereco(endereco);
    cliente.setPlano(plano);
  }

  @Test
  void deveRetornarTodosTreinos() {
    Treino treino1 = new Treino();
    treino1.setCliente(cliente);
    Treino treino2 = new Treino();
    treino2.setCliente(cliente);
    when(treinoRepo.findAll()).thenReturn(Arrays.asList(treino1, treino2));

    ResponseEntity<List<Treino>> resposta = controlador.findAll();
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Treino> treinos = resposta.getBody();
    assertNotNull(treinos);
    assertEquals(2, treinos.size());
  }

  @Test
  void deveCriarTreino() {
    Treino treino = new Treino();
    when(treinoRepo.save(any(Treino.class))).thenReturn(treino);

    ResponseEntity<Treino> resposta = controlador.save(treino);
    assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveRetornarTreinoPorId() {
    Treino treino = new Treino();
    when(treinoRepo.findById(1L)).thenReturn(Optional.of(treino));

    ResponseEntity<Treino> resposta = controlador.getById(1L);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveLancarExcecaoQuandoTreinoNaoEncontrado() {
    when(treinoRepo.findById(1L)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controlador.getById(1L));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  @Test
  void deveAtualizarTreino() {
    Treino treino = new Treino();
    when(treinoRepo.findById(1L)).thenReturn(Optional.of(treino));
    when(treinoRepo.save(any(Treino.class))).thenReturn(treino);

    ResponseEntity<Treino> resposta = controlador.update(1L, treino);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveExcluirTreino() {
    doNothing().when(treinoRepo).deleteById(1L);

    ResponseEntity<Void> resposta = controlador.delete(1L);
    assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    verify(treinoRepo, times(1)).deleteById(1L);
  }
}
