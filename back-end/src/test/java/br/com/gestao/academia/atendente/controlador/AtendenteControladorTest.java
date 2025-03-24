package br.com.gestao.academia.atendente.controlador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
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

  @Test
  void deveLancarExcecaoAoCriarAtendenteComCpfDuplicado() {
    Atendente atendente = new Atendente();
    atendente.setCpf("12345678901");
    atendente.setRg("987654321");
    atendente.setLogin("login");
    atendente.setEndereco(endereco);
    when(atendenteRepo.existsByCpf(atendente.getCpf())).thenReturn(true);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> controlador.create(atendente));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("CPF já está em uso", exception.getReason());
  }

  @Test
  void deveLancarExcecaoAoCriarAtendenteComRgDuplicado() {
    Atendente atendente = new Atendente();
    atendente.setCpf("12345678901");
    atendente.setRg("987654321");
    atendente.setLogin("login");
    atendente.setEndereco(endereco);
    when(atendenteRepo.existsByCpf(atendente.getCpf())).thenReturn(false);
    when(atendenteRepo.existsByRg(atendente.getRg())).thenReturn(true);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> controlador.create(atendente));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("RG já está em uso", exception.getReason());
  }

  @Test
  void deveLancarExcecaoAoCriarAtendenteComLoginDuplicado() {
    Atendente atendente = new Atendente();
    atendente.setCpf("12345678901");
    atendente.setRg("987654321");
    atendente.setLogin("login");
    atendente.setEndereco(endereco);
    when(atendenteRepo.existsByCpf(atendente.getCpf())).thenReturn(false);
    when(atendenteRepo.existsByRg(atendente.getRg())).thenReturn(false);
    when(atendenteRepo.existsByLogin(atendente.getLogin())).thenReturn(true);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> controlador.create(atendente));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("Login já está em uso", exception.getReason());
  }

  @Test
  void deveLancarExcecaoNaAtualizacaoComCpfDuplicado() {
    Atendente existing = new Atendente();
    existing.setCpf("11111111111");
    existing.setRg("111111111");
    existing.setLogin("loginOld");
    existing.setEndereco(endereco);
    when(atendenteRepo.findById(1L)).thenReturn(Optional.of(existing));

    Atendente updated = new Atendente();
    updated.setCpf("22222222222"); // CPF alterado
    updated.setRg("111111111"); // inalterado
    updated.setLogin("loginOld"); // inalterado
    updated.setEndereco(endereco);
    when(atendenteRepo.existsByCpf(updated.getCpf())).thenReturn(true);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> controlador.update(1L, updated));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("CPF já está em uso", exception.getReason());
  }

  @Test
  void deveLancarExcecaoNaAtualizacaoComRgDuplicado() {
    Atendente existing = new Atendente();
    existing.setCpf("11111111111");
    existing.setRg("111111111");
    existing.setLogin("loginOld");
    existing.setEndereco(endereco);
    when(atendenteRepo.findById(1L)).thenReturn(Optional.of(existing));

    Atendente updated = new Atendente();
    updated.setCpf("11111111111"); // inalterado
    updated.setRg("222222222"); // RG alterado
    updated.setLogin("loginOld"); // inalterado
    updated.setEndereco(endereco);
    when(atendenteRepo.existsByRg(updated.getRg())).thenReturn(true);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> controlador.update(1L, updated));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("RG já está em uso", exception.getReason());
  }

  @Test
  void deveLancarExcecaoNaAtualizacaoComLoginDuplicado() {
    Atendente existing = new Atendente();
    existing.setCpf("11111111111");
    existing.setRg("111111111");
    existing.setLogin("loginOld");
    existing.setEndereco(endereco);
    when(atendenteRepo.findById(1L)).thenReturn(Optional.of(existing));

    Atendente updated = new Atendente();
    updated.setCpf("11111111111"); // inalterado
    updated.setRg("111111111"); // inalterado
    updated.setLogin("newLogin"); // Login alterado
    updated.setEndereco(endereco);
    when(atendenteRepo.existsByLogin(updated.getLogin())).thenReturn(true);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> controlador.update(1L, updated));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("Login já está em uso", exception.getReason());
  }

  @Test
  void deveBuscarTodosQuandoNomeECpfVazios() {
    List<Atendente> lista = Arrays.asList(new Atendente(), new Atendente());
    when(atendenteRepo.findAll()).thenReturn(lista);
    ResponseEntity<List<Atendente>> resposta = controlador.search(null, null);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertEquals(lista, resposta.getBody());
  }

  @Test
  void deveBuscarPeloNomeECpfQuandoAmbosPreenchidos() {
    String nome = "João";
    String cpf = "12345678901";
    List<Atendente> lista = Collections.singletonList(new Atendente());
    when(atendenteRepo.findByNomeContainingIgnoreCaseAndCpfContainingIgnoreCase(nome, cpf))
        .thenReturn(lista);
    ResponseEntity<List<Atendente>> resposta = controlador.search(nome, cpf);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertEquals(lista, resposta.getBody());
  }

  @Test
  void deveBuscarPeloNomeQuandoApenasNomePreenchido() {
    String nome = "João";
    List<Atendente> lista = Collections.singletonList(new Atendente());
    when(atendenteRepo.findByNomeContainingIgnoreCase(nome))
        .thenReturn(lista);
    ResponseEntity<List<Atendente>> resposta = controlador.search(nome, null);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertEquals(lista, resposta.getBody());
  }

  @Test
  void deveBuscarPeloCpfQuandoApenasCpfPreenchido() {
    String cpf = "12345678901";
    List<Atendente> lista = Collections.singletonList(new Atendente());
    when(atendenteRepo.findByCpfContainingIgnoreCase(cpf))
        .thenReturn(lista);
    ResponseEntity<List<Atendente>> resposta = controlador.search(null, cpf);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertEquals(lista, resposta.getBody());
  }

  @Test
  void deveAtualizarAtendenteComAlteracoesEmTodosOsCamposSemConflito() {
    // Cenário em que os três campos são alterados, mas não existe conflito.
    Atendente existing = new Atendente();
    existing.setCpf("11111111111");
    existing.setRg("111111111");
    existing.setLogin("loginOld");
    existing.setEndereco(endereco);

    when(atendenteRepo.findById(1L)).thenReturn(Optional.of(existing));

    Atendente updated = new Atendente();
    updated.setNome("Nome Novo");
    updated.setLogin("loginNew"); // alterado
    updated.setSenha("novaSenha");
    updated.setCpf("22222222222"); // alterado
    updated.setRg("222222222"); // alterado
    updated.setDataNascimento(null);
    updated.setEmail("novo@email.com");
    updated.setTelefone("+5511999999999");
    updated.setEndereco(endereco); // inalterado

    // Simula que não existe conflito para os campos alterados
    when(atendenteRepo.existsByCpf(updated.getCpf())).thenReturn(false);
    when(atendenteRepo.existsByRg(updated.getRg())).thenReturn(false);
    when(atendenteRepo.existsByLogin(updated.getLogin())).thenReturn(false);
    when(atendenteRepo.save(any(Atendente.class))).thenReturn(updated);

    ResponseEntity<Atendente> resposta = controlador.update(1L, updated);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    Atendente result = resposta.getBody();
    assertNotNull(result);
    assertEquals("Nome Novo", result.getNome());
    assertEquals("loginNew", result.getLogin());
    assertEquals("22222222222", result.getCpf());
    assertEquals("222222222", result.getRg());
  }

  @Test
  void deveLancarExcecaoQuandoAtualizacaoNaoEncontrado() {
    Atendente atendente = new Atendente();
    atendente.setCpf("12345678901");
    atendente.setRg("123456789");
    atendente.setLogin("login");
    atendente.setEndereco(endereco);
    when(atendenteRepo.findById(1L)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> controlador.update(1L, atendente));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("Atendente não encontrado", exception.getReason());
  }
}
