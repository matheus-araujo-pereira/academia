package br.com.gestao.academia.cliente.controlador;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.util.Arrays;
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
  void deveBuscarClientesPorNomeECpf() {
    Cliente cliente = new Cliente();
    cliente.setNome("Ana");
    cliente.setEndereco(endereco);
    cliente.setPlano(plano);
    when(clienteRepo.findByNomeContainingIgnoreCaseAndCpfContainingIgnoreCase("Ana", "123"))
        .thenReturn(Arrays.asList(cliente));

    ResponseEntity<List<Cliente>> resposta = controlador.search("Ana", "123");
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Cliente> clientes = resposta.getBody();
    assertNotNull(clientes);
    assertEquals(1, clientes.size());
  }

  @Test
  void deveBuscarClientesPorCpf() {
    Cliente cliente = new Cliente();
    cliente.setNome("Bia");
    cliente.setEndereco(endereco);
    cliente.setPlano(plano);
    when(clienteRepo.findByCpfContainingIgnoreCase("456"))
        .thenReturn(Arrays.asList(cliente));

    ResponseEntity<List<Cliente>> resposta = controlador.search(null, "456");
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Cliente> clientes = resposta.getBody();
    assertNotNull(clientes);
    // Verifica que a ordenação ocorreu (nome comparado)
    assertEquals("Bia", clientes.get(0).getNome());
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
  void naoDeveCriarClienteCasoCpfJaExista() {
    Cliente cliente = new Cliente();
    cliente.setCpf("11111111111");
    cliente.setRg("rg1");
    cliente.setLogin("login1");
    cliente.setEndereco(endereco);
    cliente.setPlano(plano);
    when(clienteRepo.existsByCpf("11111111111")).thenReturn(true);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      controlador.create(cliente);
    });
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertTrue(Optional.ofNullable(exception.getReason()).orElse("").contains("CPF"));
  }

  @Test
  void naoDeveCriarClienteCasoRgJaExista() {
    Cliente cliente = new Cliente();
    cliente.setCpf("22222222222");
    cliente.setRg("rg2");
    cliente.setLogin("login2");
    cliente.setEndereco(endereco);
    cliente.setPlano(plano);
    when(clienteRepo.existsByCpf("22222222222")).thenReturn(false);
    when(clienteRepo.existsByRg("rg2")).thenReturn(true);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      controlador.create(cliente);
    });
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertTrue(Optional.ofNullable(exception.getReason()).orElse("").contains("RG"));
  }

  @Test
  void naoDeveCriarClienteCasoLoginJaExista() {
    Cliente cliente = new Cliente();
    cliente.setCpf("33333333333");
    cliente.setRg("rg3");
    cliente.setLogin("login3");
    cliente.setEndereco(endereco);
    cliente.setPlano(plano);
    when(clienteRepo.existsByCpf("33333333333")).thenReturn(false);
    when(clienteRepo.existsByRg("rg3")).thenReturn(false);
    when(clienteRepo.existsByLogin("login3")).thenReturn(true);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      controlador.create(cliente);
    });
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertTrue(Optional.ofNullable(exception.getReason()).orElse("").contains("Login"));
  }

  @Test
  void naoDeveCriarClienteCasoPlanoNaoEncontrado() {
    Cliente cliente = new Cliente();
    cliente.setCpf("44444444444");
    cliente.setRg("rg4");
    cliente.setLogin("login4");
    cliente.setEndereco(endereco);
    Plano planoInvalido = new Plano();
    planoInvalido.setId(999L);
    cliente.setPlano(planoInvalido);

    when(clienteRepo.existsByCpf("44444444444")).thenReturn(false);
    when(clienteRepo.existsByRg("rg4")).thenReturn(false);
    when(clienteRepo.existsByLogin("login4")).thenReturn(false);
    when(planoRepo.findById(999L)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      controlador.create(cliente);
    });
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertTrue(Optional.ofNullable(exception.getReason()).orElse("").contains("Plano"));
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
  void naoDeveAtualizarClienteCasoCpfDuplicado() {
    Cliente existing = new Cliente();
    existing.setCpf("55555555555");
    existing.setRg("rg5");
    existing.setLogin("login5");
    existing.setEndereco(endereco);
    existing.setPlano(plano);
    existing.setNome("Original");
    existing.setDataNascimento(LocalDate.of(1990, 1, 1));
    existing.setEmail("origem@mail.com");
    existing.setTelefone("+12345678");

    Cliente updateCli = new Cliente();
    updateCli.setCpf("66666666666"); // alterado
    updateCli.setRg("rg5");
    updateCli.setLogin("login5");
    updateCli.setEndereco(endereco);
    updateCli.setPlano(plano);
    updateCli.setNome("Atualizado");
    updateCli.setDataNascimento(LocalDate.of(1990, 1, 1));
    updateCli.setEmail("atual@mail.com");
    updateCli.setTelefone("+12345678");

    when(clienteRepo.findById(1L)).thenReturn(Optional.of(existing));
    when(clienteRepo.existsByCpf("66666666666")).thenReturn(true);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      controlador.update(1L, updateCli);
    });
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertTrue(Optional.ofNullable(exception.getReason()).orElse("").contains("CPF"));
  }

  @Test
  void naoDeveAtualizarClienteCasoRgDuplicado() {
    Cliente existing = new Cliente();
    existing.setCpf("77777777777");
    existing.setRg("rg7");
    existing.setLogin("login7");
    existing.setEndereco(endereco);
    existing.setPlano(plano);
    existing.setNome("Original");
    existing.setDataNascimento(LocalDate.of(1991, 2, 2));
    existing.setEmail("origem7@mail.com");
    existing.setTelefone("+12345679");

    Cliente updateCli = new Cliente();
    updateCli.setCpf("77777777777");
    updateCli.setRg("rg8"); // alterado
    updateCli.setLogin("login7");
    updateCli.setEndereco(endereco);
    updateCli.setPlano(plano);
    updateCli.setNome("Atualizado");
    updateCli.setDataNascimento(LocalDate.of(1991, 2, 2));
    updateCli.setEmail("atual7@mail.com");
    updateCli.setTelefone("+12345679");

    when(clienteRepo.findById(2L)).thenReturn(Optional.of(existing));
    when(clienteRepo.existsByRg("rg8")).thenReturn(true);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      controlador.update(2L, updateCli);
    });
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertTrue(Optional.ofNullable(exception.getReason()).orElse("").contains("RG"));
  }

  @Test
  void naoDeveAtualizarClienteCasoLoginDuplicado() {
    Cliente existing = new Cliente();
    existing.setCpf("88888888888");
    existing.setRg("rg8");
    existing.setLogin("login8");
    existing.setEndereco(endereco);
    existing.setPlano(plano);
    existing.setNome("Original");
    existing.setDataNascimento(LocalDate.of(1992, 3, 3));
    existing.setEmail("origem8@mail.com");
    existing.setTelefone("+12345680");

    Cliente updateCli = new Cliente();
    updateCli.setCpf("88888888888");
    updateCli.setRg("rg8");
    updateCli.setLogin("login9"); // alterado
    updateCli.setEndereco(endereco);
    updateCli.setPlano(plano);
    updateCli.setNome("Atualizado");
    updateCli.setDataNascimento(LocalDate.of(1992, 3, 3));
    updateCli.setEmail("atual8@mail.com");
    updateCli.setTelefone("+12345680");

    when(clienteRepo.findById(3L)).thenReturn(Optional.of(existing));
    when(clienteRepo.existsByLogin("login9")).thenReturn(true);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      controlador.update(3L, updateCli);
    });
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertTrue(Optional.ofNullable(exception.getReason()).orElse("").contains("Login"));
  }

  @Test
  void naoDeveAtualizarClienteCasoPlanoNaoEncontrado() {
    Cliente existing = new Cliente();
    existing.setCpf("99999999999");
    existing.setRg("rg9");
    existing.setLogin("login9");
    existing.setEndereco(endereco);
    existing.setPlano(plano);
    existing.setNome("Original");
    existing.setDataNascimento(LocalDate.of(1993, 4, 4));
    existing.setEmail("origem9@mail.com");
    existing.setTelefone("+12345681");

    Cliente updateCli = new Cliente();
    updateCli.setCpf("99999999999");
    updateCli.setRg("rg9");
    updateCli.setLogin("login9");
    updateCli.setEndereco(endereco);
    Plano planoInvalido = new Plano();
    planoInvalido.setId(1000L);
    updateCli.setPlano(planoInvalido);
    updateCli.setNome("Atualizado");
    updateCli.setDataNascimento(LocalDate.of(1993, 4, 4));
    updateCli.setEmail("atual9@mail.com");
    updateCli.setTelefone("+12345681");

    when(clienteRepo.findById(4L)).thenReturn(Optional.of(existing));
    when(planoRepo.findById(1000L)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      controlador.update(4L, updateCli);
    });
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertTrue(Optional.ofNullable(exception.getReason()).orElse("").contains("Plano"));
  }

  @Test
  void deveExcluirCliente() {
    doNothing().when(clienteRepo).deleteById(1L);

    ResponseEntity<Void> resposta = controlador.delete(1L);
    assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    verify(clienteRepo, times(1)).deleteById(1L);
  }

  @Test
  void deveBuscarTodosClientesQuandoParametrosVazios() {
    // Caso FF: tanto nome quanto cpf vazios.
    Cliente cliente1 = new Cliente();
    cliente1.setNome("Ana");
    cliente1.setEndereco(endereco);
    cliente1.setPlano(plano);
    Cliente cliente2 = new Cliente();
    cliente2.setNome("Bia");
    cliente2.setEndereco(endereco);
    cliente2.setPlano(plano);
    // Simula retorno não ordenado para confirmar que o método ordena
    when(clienteRepo.findAllByOrderByNomeAsc()).thenReturn(Arrays.asList(cliente2, cliente1));

    ResponseEntity<List<Cliente>> resposta = controlador.search("", "");
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Cliente> clientes = resposta.getBody();
    assertNotNull(clientes);
    // Espera-se ordem alfabética: "Ana" antes de "Bia"
    assertEquals("Ana", clientes.get(0).getNome());
    assertEquals("Bia", clientes.get(1).getNome());
  }

  @Test
  void deveAtualizarClienteComCamposDiferentesNaoDuplicados() {
    // Cenário: valores de CPF, RG e Login são alterados (VF,FV) mas não possuem
    // duplicata (caso FF no teste de duplicidade)
    Cliente existing = new Cliente();
    existing.setCpf("12345678901");
    existing.setRg("123456789");
    existing.setLogin("login");
    existing.setEndereco(endereco);
    existing.setPlano(plano);
    existing.setNome("Original");
    existing.setDataNascimento(LocalDate.of(1990, 1, 1));
    existing.setEmail("origem@mail.com");
    existing.setTelefone("+12345678");

    Cliente updateCli = new Cliente();
    updateCli.setCpf("32109876543"); // novo valor (diferente)
    updateCli.setRg("987654321"); // novo valor (diferente)
    updateCli.setLogin("novoLogin"); // novo valor (diferente)
    updateCli.setEndereco(endereco);
    updateCli.setPlano(plano);
    updateCli.setNome("Atualizado");
    updateCli.setDataNascimento(LocalDate.of(1992, 2, 2));
    updateCli.setEmail("novo@mail.com");
    updateCli.setTelefone("+87654321");

    when(clienteRepo.findById(10L)).thenReturn(Optional.of(existing));
    // CPF mudou, mas não existe duplicata
    when(clienteRepo.existsByCpf("32109876543")).thenReturn(false);
    // RG mudou, mas não existe duplicata
    when(clienteRepo.existsByRg("987654321")).thenReturn(false);
    // Login mudou, mas não existe duplicata
    when(clienteRepo.existsByLogin("novoLogin")).thenReturn(false);
    when(planoRepo.findById(plano.getId())).thenReturn(Optional.of(plano));
    when(clienteRepo.save(any(Cliente.class))).thenReturn(updateCli);

    ResponseEntity<Cliente> resposta = controlador.update(10L, updateCli);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    Cliente resultado = resposta.getBody();
    assertNotNull(resultado);
    assertEquals("32109876543", resultado.getCpf());
    assertEquals("987654321", resultado.getRg());
    assertEquals("novoLogin", resultado.getLogin());
    assertEquals("Atualizado", resultado.getNome());
  }

  @Test
  void deveAtualizarClienteComMixDeCampos() {
    // Configurar um cenário onde:
    // - CPF permanece inalterado (não dispara verificação)
    // - RG é alterado e não possui duplicata
    // - Login permanece inalterado (não dispara verificação)
    Cliente existing = new Cliente();
    existing.setCpf("11111111111");
    existing.setRg("22222222222");
    existing.setLogin("user1");
    existing.setEndereco(endereco);
    existing.setPlano(plano);
    existing.setNome("Original");
    existing.setDataNascimento(LocalDate.of(1990, 1, 1));
    existing.setEmail("original@mail.com");
    existing.setTelefone("+12345678");

    Cliente updateCli = new Cliente();
    updateCli.setCpf("11111111111"); // igual
    updateCli.setRg("33333333333"); // alterado
    updateCli.setLogin("user1"); // igual
    updateCli.setEndereco(endereco);
    updateCli.setPlano(plano);
    updateCli.setNome("Atualizado");
    updateCli.setDataNascimento(LocalDate.of(1991, 2, 2));
    updateCli.setEmail("atualizado@mail.com");
    updateCli.setTelefone("+87654321");

    when(clienteRepo.findById(20L)).thenReturn(Optional.of(existing));
    // CPF inalterado: nenhuma verificação de duplicata realizada.
    // RG alterado: simula que não há duplicata.
    when(clienteRepo.existsByRg("33333333333")).thenReturn(false);
    // Login inalterado.
    when(planoRepo.findById(plano.getId())).thenReturn(Optional.of(plano));
    when(clienteRepo.save(any(Cliente.class))).thenReturn(updateCli);

    ResponseEntity<Cliente> resposta = controlador.update(20L, updateCli);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    Cliente resultado = resposta.getBody();
    assertNotNull(resultado);
    assertEquals("11111111111", resultado.getCpf());
    assertEquals("33333333333", resultado.getRg());
    assertEquals("user1", resultado.getLogin());
    assertEquals("Atualizado", resultado.getNome());
  }

  @Test
  void deveBuscarClientesQuandoParametrosComEspacos() {
    // Passa parâmetros que são apenas espaços, que serão transformados em strings
    // vazias
    Cliente cliente = new Cliente();
    cliente.setNome("Carlos");
    cliente.setEndereco(endereco);
    cliente.setPlano(plano);
    when(clienteRepo.findAllByOrderByNomeAsc()).thenReturn(Arrays.asList(cliente));

    ResponseEntity<List<Cliente>> resposta = controlador.search("    ", "   ");
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Cliente> clientes = resposta.getBody();
    assertNotNull(clientes);
    assertEquals("Carlos", clientes.get(0).getNome());
  }

  @Test
  public void update_clienteNaoEncontrado_deveLancarExcecao() {
    Long idInexistente = 999L;
    Cliente cliente = new Cliente();
    // ...atribuição de propriedades mínimas se necessário...
    when(clienteRepo.findById(idInexistente)).thenReturn(Optional.empty());

    ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
      controlador.update(idInexistente, cliente);
    });
    assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    assertEquals("Cliente não encontrado", ex.getReason());
  }
}
