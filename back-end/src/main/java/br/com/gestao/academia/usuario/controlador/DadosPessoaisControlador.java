package br.com.gestao.academia.usuario.controlador;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dados-pessoais")
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem
public class DadosPessoaisControlador {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDadosPessoais() {
        // Exemplo dummy. Na prática, obtenha os dados do usuário autenticado.
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", "Usuário Exemplo");
        dados.put("email", "exemplo@dominio.com");
        dados.put("tipoUsuario", "administrador");
        return new ResponseEntity<>(dados, HttpStatus.OK);
    }
}
