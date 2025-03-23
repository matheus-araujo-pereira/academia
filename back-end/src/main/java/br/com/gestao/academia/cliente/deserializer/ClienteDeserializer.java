package br.com.gestao.academia.cliente.deserializer;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import br.com.gestao.academia.cliente.modelo.Cliente;

public class ClienteDeserializer extends JsonDeserializer<Cliente> {
  @Override
  public Cliente deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    Long id;
    if (p.getCurrentToken().isNumeric()) {
      id = p.getLongValue();
    } else {
      id = Long.parseLong(p.getText());
    }
    Cliente cliente = new Cliente();
    cliente.setId(id);
    return cliente;
  }
}
