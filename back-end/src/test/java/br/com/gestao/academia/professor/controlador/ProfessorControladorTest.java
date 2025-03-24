package br.com.gestao.academia.professor.controlador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import br.com.gestao.academia.endereco.modelo.Endereco;
import br.com.gestao.academia.professor.modelo.Professor;
import br.com.gestao.academia.professor.repositorio.ProfessorRepositorio;

@ExtendWith(MockitoExtension.class)
class ProfessorControladorTest {

  @Mock
  private ProfessorRepositorio professorRepo;

  @InjectMocks
  private ProfessorControlador controlador;

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
  void deveRetornarTodosProfessores() {
    Professor professor1 = new Professor();
    professor1.setEndereco(endereco);
    Professor professor2 = new Professor();
    professor2.setEndereco(endereco);
    when(professorRepo.findAllByOrderByNomeAsc()).thenReturn(Arrays.asList(professor1, professor2));

    ResponseEntity<List<Professor>> resposta = controlador.findAll();
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Professor> professores = resposta.getBody();
    assertNotNull(professores);
    assertEquals(2, professores.size());
  }

  @Test
  void deveCriarProfessor() {
    Professor professor = new Professor();
    professor.setEndereco(endereco);
    when(professorRepo.save(any(Professor.class))).thenReturn(professor);

    ResponseEntity<Professor> resposta = controlador.save(professor);
    assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveRetornarProfessorPorId() {
    Professor professor = new Professor();
    professor.setEndereco(endereco);
    when(professorRepo.findById(1L)).thenReturn(Optional.of(professor));

    ResponseEntity<Professor> resposta = controlador.getById(1L);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveLancarExcecaoQuandoProfessorNaoEncontrado() {
    when(professorRepo.findById(1L)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controlador.getById(1L));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  @Test
  void deveAtualizarProfessor() {
    Professor professor = new Professor();
    professor.setCpf("12345678901");
    professor.setRg("123456789");
    professor.setLogin("login");
    professor.setEndereco(endereco);
    when(professorRepo.findById(1L)).thenReturn(Optional.of(professor));
    when(professorRepo.save(any(Professor.class))).thenReturn(professor);

    ResponseEntity<Professor> resposta = controlador.update(1L, professor);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  @Test
  void deveExcluirProfessor() {
    doNothing().when(professorRepo).deleteById(1L);

    ResponseEntity<Void> resposta = controlador.delete(1L);
    assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    verify(professorRepo, times(1)).deleteById(1L);
  }

  // Testes para @GetMapping("/search")
  @Test
  void devePesquisarProfessoresAmbosVazios() {
    List<Professor> professores = Arrays.asList(new Professor() {
      {
        setNome("Ana");
      }
    },
        new Professor() {
          {
            setNome("Bruno");
          }
        });
    when(professorRepo.findAll()).thenReturn(professores);

    ResponseEntity<List<Professor>> resposta = controlador.search("", "");
    List<Professor> corpo = resposta.getBody(); // previne potential null pointer
    assertNotNull(corpo);
    assertEquals(2, corpo.size());
  }

  @Test
  void devePesquisarPorNomeSomente() {
    List<Professor> professores = Collections.singletonList(new Professor() {
      {
        setNome("Carlos");
      }
    });
    when(professorRepo.findByNomeContainingIgnoreCase("Carlos")).thenReturn(professores);

    ResponseEntity<List<Professor>> resposta = controlador.search("Carlos", "");
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    List<Professor> corpo = resposta.getBody(); // previne NullPointerException
    assertNotNull(corpo);
    assertEquals(1, corpo.size());
  }

  @Test
  void devePesquisarPorCpfSomente() {
    List<Professor> professores = Collections.singletonList(new Professor() {
      {
        setNome("Daniel");
      }
    });
    when(professorRepo.findByCpfContainingIgnoreCase("11111111111")).thenReturn(professores);

    ResponseEntity<List<Professor>> resposta = controlador.search("", "11111111111");
    List<Professor> corpo = resposta.getBody(); // previne NullPointerException
    assertNotNull(corpo);
    assertEquals(1, corpo.size());
  }

  @Test
  void devePesquisarPorNomeECpf() {
    List<Professor> professores = Collections.singletonList(new Professor() {
      {
        setNome("Eduardo");
      }
    });
    when(professorRepo.findByNomeContainingIgnoreCaseAndCpfContainingIgnoreCase("Eduardo", "22222222222"))
        .thenReturn(professores);

    ResponseEntity<List<Professor>> resposta = controlador.search("Eduardo", "22222222222");
    List<Professor> corpo = resposta.getBody(); // previne NullPointerException
    assertNotNull(corpo, "O corpo da resposta não deve ser nulo");
    assertEquals(1, corpo.size());
  }

  // Testes para @PostMapping (save)
  @Test
  void deveLancarExcecaoAoCriarProfessorComCpfExistente() {
    Professor professor = new Professor();
    professor.setCpf("12345678901");
    professor.setRg("987654321");
    professor.setLogin("user1");
    professor.setEndereco(endereco);

    when(professorRepo.existsByCpf("12345678901")).thenReturn(true);

    ResponseStatusException ex = assertThrows(ResponseStatusException.class,
        () -> controlador.save(professor));
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    String reason = ex.getReason();
    assertNotNull(reason, "O motivo da exceção não deve ser nulo");
    assertTrue(reason.contains("CPF"));
  }

  @Test
  void deveLancarExcecaoAoCriarProfessorComRgExistente() {
    Professor professor = new Professor();
    professor.setCpf("12345678901");
    professor.setRg("987654321");
    professor.setLogin("user1");
    professor.setEndereco(endereco);

    when(professorRepo.existsByCpf("12345678901")).thenReturn(false);
    when(professorRepo.existsByRg("987654321")).thenReturn(true);

    ResponseStatusException ex = assertThrows(ResponseStatusException.class,
        () -> controlador.save(professor));
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    String reason = ex.getReason();
    assertNotNull(reason, "O motivo da exceção não deve ser nulo");
    assertTrue(reason.contains("RG"));
  }

  @Test
  void deveLancarExcecaoAoCriarProfessorComLoginExistente() {
    Professor professor = new Professor();
    professor.setCpf("12345678901");
    professor.setRg("987654321");
    professor.setLogin("user1");
    professor.setEndereco(endereco);

    when(professorRepo.existsByCpf("12345678901")).thenReturn(false);
    when(professorRepo.existsByRg("987654321")).thenReturn(false);
    when(professorRepo.existsByLogin("user1")).thenReturn(true);

    ResponseStatusException ex = assertThrows(ResponseStatusException.class,
        () -> controlador.save(professor));
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    String reason = ex.getReason();
    assertNotNull(reason, "O motivo da exceção não deve ser nulo");
    assertTrue(reason.contains("Login"));
  }

  // Testes para @PutMapping (update)
  @Test
  void deveLancarExcecaoAoAtualizarProfessorComCpfExistente() {
    Professor existing = new Professor();
    existing.setCpf("12345678901");
    existing.setRg("111111111");
    existing.setLogin("loginOld");
    existing.setEndereco(endereco);

    Professor novo = new Professor();
    novo.setCpf("22222222222"); // cpf diferente
    novo.setRg(existing.getRg());
    novo.setLogin(existing.getLogin());
    novo.setNome("Teste");
    novo.setSenha("senha");
    novo.setDataNascimento(existing.getDataNascimento());
    novo.setEmail("teste@example.com");
    novo.setTelefone("12345678");
    novo.setEndereco(endereco);

    when(professorRepo.findById(1L)).thenReturn(Optional.of(existing));
    when(professorRepo.existsByCpf("22222222222")).thenReturn(true);

    ResponseStatusException ex = assertThrows(ResponseStatusException.class,
        () -> controlador.update(1L, novo));
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    String reason = ex.getReason();
    assertNotNull(reason, "O motivo da exceção não deve ser nulo");
    assertTrue(reason.contains("CPF"));
  }

  @Test
  void deveLancarExcecaoAoAtualizarProfessorComRgExistente() {
    Professor existing = new Professor();
    existing.setCpf("12345678901");
    existing.setRg("111111111");
    existing.setLogin("loginOld");
    existing.setEndereco(endereco);

    Professor novo = new Professor();
    novo.setCpf(existing.getCpf());
    novo.setRg("222222222"); // rg diferente
    novo.setLogin(existing.getLogin());
    novo.setNome("Teste");
    novo.setSenha("senha");
    novo.setDataNascimento(existing.getDataNascimento());
    novo.setEmail("teste@example.com");
    novo.setTelefone("12345678");
    novo.setEndereco(endereco);

    when(professorRepo.findById(1L)).thenReturn(Optional.of(existing));
    when(professorRepo.existsByRg("222222222")).thenReturn(true);

    ResponseStatusException ex = assertThrows(ResponseStatusException.class,
        () -> controlador.update(1L, novo));
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    String reason = ex.getReason();
    assertNotNull(reason, "O motivo da exceção não deve ser nulo");
    assertTrue(reason.contains("RG"));
  }

  @Test
  void deveLancarExcecaoAoAtualizarProfessorComLoginExistente() {
    Professor existing = new Professor();
    existing.setCpf("12345678901");
    existing.setRg("111111111");
    existing.setLogin("loginOld");
    existing.setEndereco(endereco);

    Professor novo = new Professor();
    novo.setCpf(existing.getCpf());
    novo.setRg(existing.getRg());
    novo.setLogin("newLogin"); // login diferente
    novo.setNome("Teste");
    novo.setSenha("senha");
    novo.setDataNascimento(existing.getDataNascimento());
    novo.setEmail("teste@example.com");
    novo.setTelefone("12345678");
    novo.setEndereco(endereco);

    when(professorRepo.findById(1L)).thenReturn(Optional.of(existing));
    when(professorRepo.existsByLogin("newLogin")).thenReturn(true);

    ResponseStatusException ex = assertThrows(ResponseStatusException.class,
        () -> controlador.update(1L, novo));
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    String reason = ex.getReason();
    assertNotNull(reason, "O motivo da exceção não deve ser nulo");
    assertTrue(reason.contains("Login"));
  }

  @Test
  void deveAtualizarProfessorComValoresAlteradosSemConflito() {
    Professor existing = new Professor();
    existing.setCpf("12345678901");
    existing.setRg("111111111");
    existing.setLogin("loginOld");
    existing.setNome("Original");
    existing.setSenha("senhaOld");
    existing.setEndereco(endereco);

    Professor novo = new Professor();
    novo.setCpf("12345678901"); // mesmo cpf
    novo.setRg("111111111"); // mesmo rg
    novo.setLogin("loginOld"); // mesmo login
    novo.setNome("Atualizado");
    novo.setSenha("senhaNova");
    novo.setDataNascimento(existing.getDataNascimento());
    novo.setEmail("novo@example.com");
    novo.setTelefone("87654321");
    novo.setEndereco(endereco);

    when(professorRepo.findById(1L)).thenReturn(Optional.of(existing));
    when(professorRepo.save(any(Professor.class))).thenReturn(novo);

    ResponseEntity<Professor> resposta = controlador.update(1L, novo);
    Professor professorResponse = resposta.getBody(); // previne potential null pointer access
    assertNotNull(professorResponse, "O corpo da resposta não deve ser nulo");
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertEquals("Atualizado", professorResponse.getNome());
  }

  // Testes adicionais para @GetMapping("/search") para parâmetros nulos e
  // trimming
  @Test
  void devePesquisarPorNomeSomenteComCpfNulo() {
    List<Professor> professores = Collections.singletonList(new Professor() {
      {
        setNome("Carlos");
      }
    });
    when(professorRepo.findByNomeContainingIgnoreCase("Carlos")).thenReturn(professores);

    ResponseEntity<List<Professor>> resposta = controlador.search("Carlos", null);
    List<Professor> corpo = resposta.getBody();
    assertNotNull(corpo, "O corpo da resposta não deve ser nulo");
    assertEquals(1, corpo.size());
  }

  @Test
  void devePesquisarPorCpfSomenteComNomeNulo() {
    List<Professor> professores = Collections.singletonList(new Professor() {
      {
        setNome("Daniel");
      }
    });
    when(professorRepo.findByCpfContainingIgnoreCase("11111111111")).thenReturn(professores);

    ResponseEntity<List<Professor>> resposta = controlador.search(null, "11111111111");
    List<Professor> corpo = resposta.getBody();
    assertNotNull(corpo, "O corpo da resposta não deve ser nulo");
    assertEquals(1, corpo.size());
  }

  @Test
  void devePesquisarAmbosNulos() {
    List<Professor> professores = Arrays.asList(
        new Professor() {
          {
            setNome("Ana");
          }
        },
        new Professor() {
          {
            setNome("Bruno");
          }
        });
    when(professorRepo.findAll()).thenReturn(professores);

    ResponseEntity<List<Professor>> resposta = controlador.search(null, null);
    List<Professor> corpo = resposta.getBody();
    assertNotNull(corpo, "O corpo da resposta não deve ser nulo");
    assertEquals(2, corpo.size());
  }

  @Test
  void devePesquisarRemovendoEspacosEmBranco() {
    List<Professor> professores = Collections.singletonList(new Professor() {
      {
        setNome("Eduardo");
      }
    });
    when(professorRepo.findByNomeContainingIgnoreCaseAndCpfContainingIgnoreCase("Eduardo", "22222222222"))
        .thenReturn(professores);

    ResponseEntity<List<Professor>> resposta = controlador.search("  Eduardo  ", " 22222222222 ");
    List<Professor> corpo = resposta.getBody();
    assertNotNull(corpo, "O corpo da resposta não deve ser nulo");
    assertEquals(1, corpo.size());
  }

  // Testes adicionais para validar ifs no update

  // CPF - VF: CPF novo diferente e repo.existsByCpf retorna false => atualização
  // bem-sucedida.
  @Test
  void deveAtualizarProfessorComNovoCpfSemConflito() {
    Professor existing = new Professor();
    existing.setCpf("12345678901");
    existing.setRg("111111111");
    existing.setLogin("loginOld");
    existing.setNome("Original");
    existing.setEndereco(endereco);

    Professor novo = new Professor();
    novo.setCpf("11111111111"); // CPF diferente
    novo.setRg(existing.getRg());
    novo.setLogin(existing.getLogin());
    novo.setNome("Atualizado CPF VF");
    novo.setEndereco(endereco);

    when(professorRepo.findById(1L)).thenReturn(Optional.of(existing));
    when(professorRepo.existsByCpf("11111111111")).thenReturn(false);
    when(professorRepo.save(any(Professor.class))).thenReturn(novo);

    ResponseEntity<Professor> resposta = controlador.update(1L, novo);
    Professor professorResponse = resposta.getBody();
    assertNotNull(professorResponse, "O corpo da resposta não deve ser nulo");
    assertEquals("Atualizado CPF VF", professorResponse.getNome());
    assertEquals("11111111111", professorResponse.getCpf());
  }

  // CPF - FV: CPF permanece o mesmo mesmo que repo.existsByCpf retorne true =>
  // atualização bem-sucedida.
  @Test
  void deveAtualizarProfessorComMesmoCpfMesmoSeExiste() {
    Professor existing = new Professor();
    existing.setCpf("12345678901");
    existing.setRg("111111111");
    existing.setLogin("loginOld");
    existing.setNome("Original");
    existing.setEndereco(endereco);

    Professor novo = new Professor();
    novo.setCpf("12345678901"); // mesmo CPF
    novo.setRg(existing.getRg());
    novo.setLogin(existing.getLogin());
    novo.setNome("Atualizado CPF FV");
    novo.setEndereco(endereco);

    when(professorRepo.findById(1L)).thenReturn(Optional.of(existing));
    when(professorRepo.save(any(Professor.class))).thenReturn(novo);

    ResponseEntity<Professor> resposta = controlador.update(1L, novo);
    Professor professorResponse = resposta.getBody();
    assertNotNull(professorResponse, "O corpo da resposta não deve ser nulo");
    assertEquals("Atualizado CPF FV", professorResponse.getNome());
    assertEquals("12345678901", professorResponse.getCpf());
  }

  // RG - VF: RG novo diferente e repo.existsByRg retorna false => atualização
  // bem-sucedida.
  @Test
  void deveAtualizarProfessorComNovoRgSemConflito() {
    Professor existing = new Professor();
    existing.setCpf("12345678901");
    existing.setRg("111111111");
    existing.setLogin("loginOld");
    existing.setNome("Original");
    existing.setEndereco(endereco);

    Professor novo = new Professor();
    novo.setCpf(existing.getCpf());
    novo.setRg("222222222"); // RG diferente
    novo.setLogin(existing.getLogin());
    novo.setNome("Atualizado RG VF");
    novo.setEndereco(endereco);

    when(professorRepo.findById(1L)).thenReturn(Optional.of(existing));
    when(professorRepo.existsByRg("222222222")).thenReturn(false);
    when(professorRepo.save(any(Professor.class))).thenReturn(novo);

    ResponseEntity<Professor> resposta = controlador.update(1L, novo);
    Professor professorResponse = resposta.getBody();
    assertNotNull(professorResponse, "O corpo da resposta não deve ser nulo");
    assertEquals("Atualizado RG VF", professorResponse.getNome());
    assertEquals("222222222", professorResponse.getRg());
  }

  // RG - FV: RG permanece o mesmo, mesmo que repo.existsByRg retorne true =>
  // atualização bem-sucedida.
  @Test
  void deveAtualizarProfessorComMesmoRgMesmoSeExiste() {
    Professor existing = new Professor();
    existing.setCpf("12345678901");
    existing.setRg("111111111");
    existing.setLogin("loginOld");
    existing.setNome("Original");
    existing.setEndereco(endereco);

    Professor novo = new Professor();
    novo.setCpf(existing.getCpf());
    novo.setRg("111111111"); // mesmo RG
    novo.setLogin(existing.getLogin());
    novo.setNome("Atualizado RG FV");
    novo.setEndereco(endereco);

    when(professorRepo.findById(1L)).thenReturn(Optional.of(existing));
    when(professorRepo.save(any(Professor.class))).thenReturn(novo);

    ResponseEntity<Professor> resposta = controlador.update(1L, novo);
    Professor professorResponse = resposta.getBody();
    assertNotNull(professorResponse, "O corpo da resposta não deve ser nulo");
    assertEquals("Atualizado RG FV", professorResponse.getNome());
    assertEquals("111111111", professorResponse.getRg());
  }

  // Login - VF: Novo login diferente e repo.existsByLogin retorna false =>
  // atualização bem-sucedida.
  @Test
  void deveAtualizarProfessorComNovoLoginSemConflito() {
    Professor existing = new Professor();
    existing.setCpf("12345678901");
    existing.setRg("111111111");
    existing.setLogin("loginOld");
    existing.setNome("Original");
    existing.setEndereco(endereco);

    Professor novo = new Professor();
    novo.setCpf(existing.getCpf());
    novo.setRg(existing.getRg());
    novo.setLogin("newLogin"); // login diferente
    novo.setNome("Atualizado Login VF");
    novo.setEndereco(endereco);

    when(professorRepo.findById(1L)).thenReturn(Optional.of(existing));
    when(professorRepo.existsByLogin("newLogin")).thenReturn(false);
    when(professorRepo.save(any(Professor.class))).thenReturn(novo);

    ResponseEntity<Professor> resposta = controlador.update(1L, novo);
    Professor professorResponse = resposta.getBody();
    assertNotNull(professorResponse, "O corpo da resposta não deve ser nulo");
    assertEquals("Atualizado Login VF", professorResponse.getNome());
    assertEquals("newLogin", professorResponse.getLogin());
  }

  // Login - FV: Login permanece o mesmo, mesmo que repo.existsByLogin retorne
  // true => atualização bem-sucedida.
  @Test
  void deveAtualizarProfessorComMesmoLoginMesmoSeExiste() {
    Professor existing = new Professor();
    existing.setCpf("12345678901");
    existing.setRg("111111111");
    existing.setLogin("loginOld");
    existing.setNome("Original");
    existing.setEndereco(endereco);

    Professor novo = new Professor();
    novo.setCpf(existing.getCpf());
    novo.setRg(existing.getRg());
    novo.setLogin("loginOld"); // mesmo login
    novo.setNome("Atualizado Login FV");
    novo.setEndereco(endereco);

    when(professorRepo.findById(1L)).thenReturn(Optional.of(existing));
    when(professorRepo.save(any(Professor.class))).thenReturn(novo);

    ResponseEntity<Professor> resposta = controlador.update(1L, novo);
    Professor professorResponse = resposta.getBody();
    assertNotNull(professorResponse, "O corpo da resposta não deve ser nulo");
    assertEquals("Atualizado Login FV", professorResponse.getNome());
    assertEquals("loginOld", professorResponse.getLogin());
  }

  @Test
  void deveLancarExcecaoAoAtualizarProfessorQuandoNaoEncontrado() {
    // Cria um professor para atualização (valores arbitrários)
    Professor professorAtualizado = new Professor();
    professorAtualizado.setCpf("12345678901");
    professorAtualizado.setRg("111111111");
    professorAtualizado.setLogin("novoLogin");
    professorAtualizado.setNome("Atualizado");
    professorAtualizado.setEndereco(endereco);

    // Simula que o professor não existe
    when(professorRepo.findById(1L)).thenReturn(Optional.empty());

    ResponseStatusException ex = assertThrows(ResponseStatusException.class,
        () -> controlador.update(1L, professorAtualizado));
    assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    assertNotNull(ex.getReason(), "O motivo da exceção não deve ser nulo");
    assertEquals("Professor não encontrado", ex.getReason());
  }
}
