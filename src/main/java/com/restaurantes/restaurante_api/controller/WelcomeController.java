package com.restaurantes.restaurante_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> welcome() {
        return ResponseEntity.ok(Map.of(
                "mensagem", "API Restaurante",
                "documentacao", "/swagger-ui.html",
                "categorias", "/api/categorias",
                "porta", "Use a porta 8081 se estiver em 8080"
        ));
    }
}
