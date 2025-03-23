package br.com.gestao.academia.administrador.controlador;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.gestao.academia.administrador.modelo.Administrador;
import br.com.gestao.academia.administrador.repositorio.AdministradorRepositorio;

@ExtendWith(MockitoExtension.class)
class AdministradorControladorTest {

  @Mock
  private AdministradorRepositorio adminRepo;

  @InjectMocks
  private AdministradorControlador controlador;

  @BeforeEach
  void setup() {
    // Inicializa o controlador com os mocks
  }

  @Test
  void deveRetornarTodosAdministradores() {
    Administrador admin1 = new Administrador();
    Administrador admin2 = new Administrador();
    when(adminRepo.findAll()).thenReturn(Arrays.asList(admin1, admin2));

    List<Administrador> administradores = controlador.findAll();
    assertEquals(2, administradores.size());
  }

  @Test
  void deveSalvarAdministrador() {
    Administrador admin = new Administrador();
    when(adminRepo.save(any(Administrador.class))).thenReturn(admin);

    Administrador salvo = controlador.save(admin);
    assertNotNull(salvo);
  }

  @Test
  void deveRetornarAdministradorPorId() {
    Administrador admin = new Administrador();
    when(adminRepo.findById(1L)).thenReturn(Optional.of(admin));

    Administrador encontrado = controlador.findById(1L);
    assertNotNull(encontrado);
  }

  @Test
  void deveLancarExcecaoQuandoAdministradorNaoEncontrado() {
    when(adminRepo.findById(1L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> controlador.findById(1L));
    assertEquals("Administrador não encontrado para o ID: 1", exception.getMessage());
  }

  @Test
  void deveAtualizarAdministrador() {
    Administrador admin = new Administrador();
    when(adminRepo.findById(1L)).thenReturn(Optional.of(admin));
    when(adminRepo.save(any(Administrador.class))).thenReturn(admin);

    Administrador atualizado = controlador.update(1L, admin);
    assertNotNull(atualizado);
  }

  @Test
  void deveExcluirAdministrador() {
    Administrador admin = new Administrador();
    when(adminRepo.findById(1L)).thenReturn(Optional.of(admin));
    doNothing().when(adminRepo).delete(admin);

    controlador.delete(1L);
    verify(adminRepo, times(1)).delete(admin);
  }
}
