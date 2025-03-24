package br.com.gestao.academia.treino.controlador;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

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

  @Test
  void testSearchSemFiltros() {
    // Cenário sem filtros: parâmetros nulos
    Treino treino1 = new Treino();
    treino1.setDescricao("A");
    treino1.setDataCriacao(LocalDate.now());
    treino1.setCliente(cliente);
    Treino treino2 = new Treino();
    treino2.setDescricao("B");
    treino2.setDataCriacao(LocalDate.now());
    treino2.setCliente(cliente);
    when(treinoRepo.findAll()).thenReturn(Arrays.asList(treino2, treino1));

    ResponseEntity<List<Treino>> resposta = controlador.search(null, null);
    assertNotNull(resposta.getBody()); // Evita NPE
    List<Treino> treinos = resposta.getBody();
    assertNotNull(treinos);
    assertEquals(2, treinos.size());
  }

  @Test
  void testSearchPorDescricao() {
    Treino treino1 = new Treino();
    treino1.setDescricao("Treino de Força");
    treino1.setDataCriacao(LocalDate.now());
    treino1.setCliente(cliente);
    Treino treino2 = new Treino();
    treino2.setDescricao("Cardio intenso");
    treino2.setDataCriacao(LocalDate.now());
    treino2.setCliente(cliente);
    when(treinoRepo.findAll()).thenReturn(Arrays.asList(treino1, treino2));

    ResponseEntity<List<Treino>> resposta = controlador.search("Força", null);
    assertNotNull(resposta.getBody()); // Evita NPE
    List<Treino> treinos = resposta.getBody();
    assertNotNull(treinos);
    assertEquals(1, treinos.size());
    assertTrue(treinos.get(0).getDescricao().contains("Força"));
  }

  @Test
  void testSearchPorCliente() {
    Cliente cliente2 = new Cliente();
    cliente2.setNome("Outro Cliente");
    Treino treino1 = new Treino();
    treino1.setDescricao("Treino A");
    treino1.setDataCriacao(LocalDate.now());
    treino1.setCliente(cliente);
    Treino treino2 = new Treino();
    treino2.setDescricao("Treino B");
    treino2.setDataCriacao(LocalDate.now());
    treino2.setCliente(cliente2);
    when(treinoRepo.findAll()).thenReturn(Arrays.asList(treino1, treino2));

    ResponseEntity<List<Treino>> resposta = controlador.search(null, "Cliente Teste");
    List<Treino> treinos = java.util.Objects.requireNonNull(resposta.getBody()); // Garante que treinos não é nulo
    assertEquals(1, treinos.size());
    assertEquals("Cliente Teste", treinos.get(0).getCliente().getNome());
  }

  @Test
  void testSearchPorDescricaoECliente() {
    Cliente cliente2 = new Cliente();
    cliente2.setNome("Cliente Teste");
    Treino treino1 = new Treino();
    treino1.setDescricao("Musculação avançada");
    treino1.setDataCriacao(LocalDate.now());
    treino1.setCliente(cliente);
    Treino treino2 = new Treino();
    treino2.setDescricao("Aula de Yoga");
    treino2.setDataCriacao(LocalDate.now());
    treino2.setCliente(cliente2);
    when(treinoRepo.findAll()).thenReturn(Arrays.asList(treino1, treino2));

    ResponseEntity<List<Treino>> resposta = controlador.search("Musculação", "Teste");
    assertNotNull(resposta.getBody()); // Evita NPE
    List<Treino> treinos = resposta.getBody();
    assertNotNull(treinos);
    assertEquals(1, treinos.size());
    assertTrue(treinos.get(0).getDescricao().contains("Musculação"));
  }

  @Test
  void testAtualizarTreinoComExerciciosExistentes() {
    Treino existente = new Treino();
    existente.setDescricao("Original");
    existente.setDataCriacao(LocalDate.of(2020, 1, 1));
    existente.setCliente(cliente);
    existente.setProfessor(null);
    existente.setExercicios(new ArrayList<>());
    // Adiciona um exercício existente
    Exercicio exAntigo = new Exercicio();
    exAntigo.setId(100L);
    existente.getExercicios().add(exAntigo);

    Treino treinoAtualizar = new Treino();
    treinoAtualizar.setDescricao("Atualizado");
    treinoAtualizar.setDataCriacao(LocalDate.of(2021, 1, 1));
    treinoAtualizar.setCliente(cliente);
    treinoAtualizar.setProfessor(null);
    // No update, novo exercício
    Exercicio exNovo = new Exercicio();
    exNovo.setId(200L);
    treinoAtualizar.setExercicios(Arrays.asList(exNovo));

    when(treinoRepo.findById(1L)).thenReturn(Optional.of(existente));
    when(treinoRepo.save(any(Treino.class))).thenReturn(treinoAtualizar);

    ResponseEntity<Treino> resposta = controlador.update(1L, treinoAtualizar);
    Treino atualizado = resposta.getBody();
    assertNotNull(atualizado); // Linha adicionada para evitar NPE
    assertEquals("Atualizado", atualizado.getDescricao());
    // Verifica que a lista foi substituída
    assertEquals(1, existente.getExercicios().size());
    assertEquals(200L, existente.getExercicios().get(0).getId());
    // Verifica que o treino foi setado no novo exercício
    assertEquals(existente, existente.getExercicios().get(0).getTreino());
  }

  @Test
  void testAtualizarTreinoSemExerciciosNovos() {
    Treino existente = new Treino();
    existente.setDescricao("Original");
    existente.setDataCriacao(LocalDate.of(2020, 1, 1));
    existente.setCliente(cliente);
    existente.setProfessor(null);
    existente.setExercicios(new ArrayList<>());
    // Exemplo com exercício que será removido
    Exercicio exAntigo = new Exercicio();
    exAntigo.setId(100L);
    existente.getExercicios().add(exAntigo);

    Treino treinoAtualizar = new Treino();
    treinoAtualizar.setDescricao("Atualizado");
    treinoAtualizar.setDataCriacao(LocalDate.of(2021, 1, 1));
    treinoAtualizar.setCliente(cliente);
    treinoAtualizar.setProfessor(null);
    // Não informa novos exercícios
    treinoAtualizar.setExercicios(null);

    when(treinoRepo.findById(1L)).thenReturn(Optional.of(existente));
    when(treinoRepo.save(any(Treino.class))).thenReturn(treinoAtualizar);

    ResponseEntity<Treino> resposta = controlador.update(1L, treinoAtualizar);
    assertNotNull(resposta.getBody()); // Evita NPE
    // Ao não adicionar novos exercícios, a lista deve permanecer vazia
    assertTrue(existente.getExercicios().isEmpty());
  }

  @Test
  void testAtualizarTreinoComMultiplosExercicios() {
    // Prepara o treino existente com lista vazia de exercícios
    Treino existente = new Treino();
    existente.setDescricao("Original");
    existente.setDataCriacao(LocalDate.of(2020, 1, 1));
    existente.setCliente(cliente);
    existente.setProfessor(null);
    existente.setExercicios(new ArrayList<>());

    // Prepara o treino a atualizar com dois novos exercícios
    Treino treinoAtualizar = new Treino();
    treinoAtualizar.setDescricao("Atualizado");
    treinoAtualizar.setDataCriacao(LocalDate.of(2021, 1, 1));
    treinoAtualizar.setCliente(cliente);
    treinoAtualizar.setProfessor(null);
    Exercicio ex1 = new Exercicio();
    ex1.setId(101L);
    Exercicio ex2 = new Exercicio();
    ex2.setId(102L);
    treinoAtualizar.setExercicios(Arrays.asList(ex1, ex2));

    when(treinoRepo.findById(1L)).thenReturn(Optional.of(existente));
    when(treinoRepo.save(any(Treino.class))).thenReturn(treinoAtualizar);

    ResponseEntity<Treino> resposta = controlador.update(1L, treinoAtualizar);
    Treino atualizado = resposta.getBody();
    assertNotNull(atualizado);
    assertEquals("Atualizado", atualizado.getDescricao());
    // Verifica que cada exercício teve seu treino definido
    assertEquals(2, existente.getExercicios().size());
    assertEquals(101L, existente.getExercicios().get(0).getId());
    assertEquals(102L, existente.getExercicios().get(1).getId());
    assertEquals(existente, existente.getExercicios().get(0).getTreino());
    assertEquals(existente, existente.getExercicios().get(1).getTreino());
  }

  @Test
  void testUpdateLambdaExercicios() {
    // Testa o lambda$5() que atualiza os exercícios em update()
    Treino existente = new Treino();
    existente.setDescricao("Original");
    existente.setDataCriacao(LocalDate.of(2020, 1, 1));
    existente.setCliente(cliente);
    existente.setProfessor(null);
    existente.setExercicios(new ArrayList<>());

    Treino treinoUpdate = new Treino();
    treinoUpdate.setDescricao("Atualizado");
    treinoUpdate.setDataCriacao(LocalDate.of(2021, 1, 1));
    treinoUpdate.setCliente(cliente);
    treinoUpdate.setProfessor(null);
    // Define exercícios para atualização
    Exercicio ex1 = new Exercicio();
    ex1.setId(1L);
    Exercicio ex2 = new Exercicio();
    ex2.setId(2L);
    treinoUpdate.setExercicios(Arrays.asList(ex1, ex2));

    when(treinoRepo.findById(5L)).thenReturn(Optional.of(existente));
    when(treinoRepo.save(any(Treino.class))).thenReturn(treinoUpdate);

    ResponseEntity<Treino> response = controlador.update(5L, treinoUpdate);
    Treino updated = response.getBody();
    assertNotNull(updated);
    // Verifica se o lambda foi executado: cada exercício deve ter o treino setado
    // como 'existente'
    assertEquals(2, existente.getExercicios().size());
    for (Exercicio ex : existente.getExercicios()) {
      assertEquals(existente, ex.getTreino());
    }
  }

  @Test
  void testUpdateTreinoNaoEncontrado() {
    Treino treinoAtualizar = new Treino(); // prepara dados para atualização
    // Quando o id não existir, findById retorna vazio
    when(treinoRepo.findById(999L)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      controlador.update(999L, treinoAtualizar);
    });
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("Treino não encontrado", exception.getReason());
  }

  @Test
  void testSearchPorClienteNomeNaoContem() {
    Cliente cliente2 = new Cliente();
    cliente2.setNome("Outro Cliente");
    Treino treino1 = new Treino();
    treino1.setDescricao("Treino A");
    treino1.setDataCriacao(LocalDate.now());
    treino1.setCliente(cliente);
    Treino treino2 = new Treino();
    treino2.setDescricao("Treino B");
    treino2.setDataCriacao(LocalDate.now());
    treino2.setCliente(cliente2);
    when(treinoRepo.findAll()).thenReturn(Arrays.asList(treino1, treino2));

    ResponseEntity<List<Treino>> resposta = controlador.search(null, "Inexistente");
    List<Treino> treinos = java.util.Objects.requireNonNull(resposta.getBody());
    assertEquals(0, treinos.size());
  }

  @Test
  void testSearchPorClienteNomeContem() {
    Cliente cliente2 = new Cliente();
    cliente2.setNome("Outro Cliente");
    Treino treino1 = new Treino();
    treino1.setDescricao("Treino A");
    treino1.setDataCriacao(LocalDate.now());
    treino1.setCliente(cliente);
    Treino treino2 = new Treino();
    treino2.setDescricao("Treino B");
    treino2.setDataCriacao(LocalDate.now());
    treino2.setCliente(cliente2);
    when(treinoRepo.findAll()).thenReturn(Arrays.asList(treino1, treino2));

    ResponseEntity<List<Treino>> resposta = controlador.search(null, "Cliente Teste");
    List<Treino> treinos = java.util.Objects.requireNonNull(resposta.getBody());
    assertEquals(1, treinos.size());
    assertEquals("Cliente Teste", treinos.get(0).getCliente().getNome());
  }

  @Test
  void testSearchPorClienteNulo() {
    Treino treino1 = new Treino();
    treino1.setDescricao("Treino A");
    treino1.setDataCriacao(LocalDate.now());
    treino1.setCliente(null);
    Treino treino2 = new Treino();
    treino2.setDescricao("Treino B");
    treino2.setDataCriacao(LocalDate.now());
    treino2.setCliente(cliente);
    when(treinoRepo.findAll()).thenReturn(Arrays.asList(treino1, treino2));

    ResponseEntity<List<Treino>> resposta = controlador.search(null, "Cliente Teste");
    List<Treino> treinos = java.util.Objects.requireNonNull(resposta.getBody());
    assertEquals(1, treinos.size());
    assertEquals("Cliente Teste", treinos.get(0).getCliente().getNome());
  }
}
