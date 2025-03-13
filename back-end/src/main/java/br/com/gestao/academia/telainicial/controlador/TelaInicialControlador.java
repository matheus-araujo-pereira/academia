package br.com.gestao.academia.telainicial.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelaInicialControlador {

    @GetMapping("/")
    public String home() {
        return "Back-End Academia!";
    }
}
