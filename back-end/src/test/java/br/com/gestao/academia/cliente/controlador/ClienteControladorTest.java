package br.com.gestao.academia.cliente.controlador;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
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

import br.com.gestao.academia.cliente.modelo.Cliente;
import br.com.gestao.academia.cliente.repositorio.ClienteRepositorio;
import br.com.gestao.academia.endereco.modelo.Endereco;
import br.com.gestao.academia.plano.modelo.Plano;
import br.com.gestao.academia.plano.repositorio.PlanoRepositorio;

@ExtendWith(MockitoExtension.class)
class ClienteControladorTest {

  @Mock
  private ClienteRepositorio clienteRepo;

  @Mock
  private PlanoRepositorio planoRepo;

  @InjectMocks
  private ClienteControlador controlador;

  private Endereco endereco;
  private Plano plano;

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

    plano = new Plano();
    plano.setId(1L);
    plano.setNome("Plano Básico");
    plano.setValor(new BigDecimal("99.99"));
  }

  @Test
  void deveRetornarTodosClientes() {
    Cliente cliente1 = new Cliente();
    cliente1.setEndereco(endereco);
    cliente1.setPlano(plano);
    Cliente cliente2 = new Cliente();
    cliente2.setEndereco(endereco);
    cliente2.setPlano(plano);
    when(clienteRepo.findAllByOrderByNomeAsc()).thenReturn(Arrays.asList(cliente1, cliente2));

    ResponseEntity<List<Cliente>> resposta = controlador.findAll();
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Cliente> clientes = resposta.getBody();
    assertNotNull(clientes);
    assertEquals(2, clientes.size());
  }

  @Test
  void deveBuscarClientesPorNome() {
    Cliente cliente1 = new Cliente();
    cliente1.setNome("Cliente 1");
    cliente1.setEndereco(endereco);
    cliente1.setPlano(plano);
    Cliente cliente2 = new Cliente();
    cliente2.setNome("Cliente 2");
    cliente2.setEndereco(endereco);
    cliente2.setPlano(plano);
    when(clienteRepo.findByNomeContainingIgnoreCase("nome")).thenReturn(Arrays.asList(cliente1, cliente2));

    ResponseEntity<List<Cliente>> resposta = controlador.search("nome", null);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Cliente> clientes = resposta.getBody();
    assertNotNull(clientes);
    assertEquals(2, clientes.size());
  }

  @Test
  void deveCriarCliente() {
    Cliente cliente = new Cliente();
    cliente.setEndereco(endereco);
    cliente.setPlano(plano);
    when(planoRepo.findById(1L)).thenReturn(Optional.of(plano));
    when(clienteRepo.save(any(Cliente.class))).thenReturn(cliente);

    ResponseEntity<Cliente> resposta = controlador.create(cliente);
    assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveRetornarClientePorId() {
    Cliente cliente = new Cliente();
    cliente.setEndereco(endereco);
    cliente.setPlano(plano);
    when(clienteRepo.findById(1L)).thenReturn(Optional.of(cliente));

    ResponseEntity<Cliente> resposta = controlador.getById(1L);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveLancarExcecaoQuandoClienteNaoEncontrado() {
    when(clienteRepo.findById(1L)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controlador.getById(1L));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  @Test
  void deveAtualizarCliente() {
    Cliente cliente = new Cliente();
    cliente.setCpf("12345678901");
    cliente.setRg("123456789");
    cliente.setLogin("login");
    cliente.setEndereco(endereco);
    cliente.setPlano(plano);
    when(clienteRepo.findById(1L)).thenReturn(Optional.of(cliente));
    when(planoRepo.findById(1L)).thenReturn(Optional.of(plano));
    when(clienteRepo.save(any(Cliente.class))).thenReturn(cliente);

    ResponseEntity<Cliente> resposta = controlador.update(1L, cliente);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveExcluirCliente() {
    doNothing().when(clienteRepo).deleteById(1L);

    ResponseEntity<Void> resposta = controlador.delete(1L);
    assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    verify(clienteRepo, times(1)).deleteById(1L);
  }
}
