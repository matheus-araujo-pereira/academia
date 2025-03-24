package br.com.gestao.academia.cliente.deserializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.gestao.academia.cliente.modelo.Cliente;

class ClienteDeserializerTest {

  private ObjectMapper mapper;

  @BeforeEach
  void setup() {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Cliente.class, new ClienteDeserializer());
    mapper.registerModule(module);
  }

  @Test
  void deveDeserializarComTokenNumerico() throws Exception {
    String json = "123";
    Cliente cliente = mapper.readValue(json, Cliente.class);
    assertNotNull(cliente);
    assertEquals(123L, cliente.getId());
  }

  @Test
  void deveDeserializarComTokenTexto() throws Exception {
    String json = "\"456\"";
    Cliente cliente = mapper.readValue(json, Cliente.class);
    assertNotNull(cliente);
    assertEquals(456L, cliente.getId());
  }
}
