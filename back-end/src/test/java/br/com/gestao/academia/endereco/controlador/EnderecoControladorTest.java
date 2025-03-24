package br.com.gestao.academia.endereco.controlador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestao.academia.endereco.modelo.Endereco;
import br.com.gestao.academia.endereco.repositorio.EnderecoRepositorio;

@ExtendWith(MockitoExtension.class)
class EnderecoControladorTest {

  @Mock
  private EnderecoRepositorio enderecoRepo;

  @InjectMocks
  private EnderecoControlador controlador;

  @BeforeEach
  void setup() {
    // Inicializa o controlador com os mocks
  }

  @Test
  void deveRetornarTodosEnderecos() {
    Endereco endereco1 = new Endereco();
    Endereco endereco2 = new Endereco();
    when(enderecoRepo.findAll()).thenReturn(Arrays.asList(endereco1, endereco2));

    List<Endereco> enderecos = controlador.findAll();
    assertEquals(2, enderecos.size());
  }

  @Test
  void deveSalvarEndereco() {
    Endereco endereco = new Endereco();
    when(enderecoRepo.save(any(Endereco.class))).thenReturn(endereco);

    Endereco salvo = controlador.save(endereco);
    assertNotNull(salvo);
  }
}
