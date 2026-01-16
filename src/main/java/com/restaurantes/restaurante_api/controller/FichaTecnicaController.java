package com.restaurantes.restaurante_api.controller;

import com.restaurantes.restaurante_api.dto.FichaTecnicaDTO;
import com.restaurantes.restaurante_api.service.FichaTecnicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ficha-tecnica")
@Tag(name = "Ficha Técnica", description = "API para gerenciamento de fichas técnicas (receitas) dos produtos")
public class FichaTecnicaController {
    
    @Autowired
    private FichaTecnicaService service;
    
    @GetMapping
    @Operation(summary = "Listar todas as fichas técnicas", description = "Retorna uma lista com todas as fichas técnicas cadastradas")
    public ResponseEntity<List<FichaTecnicaDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar ficha técnica por ID", description = "Retorna uma ficha técnica específica pelo seu ID")
    public ResponseEntity<FichaTecnicaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @PostMapping
    @Operation(summary = "Criar nova ficha técnica", description = "Cria uma nova ficha técnica (receita) para um produto")
    public ResponseEntity<FichaTecnicaDTO> create(@RequestBody FichaTecnicaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ficha técnica", description = "Atualiza os dados de uma ficha técnica existente")
    public ResponseEntity<FichaTecnicaDTO> update(@PathVariable Long id, @RequestBody FichaTecnicaDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir ficha técnica", description = "Remove uma ficha técnica do sistema")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/produto/{produtoId}")
    @Operation(summary = "Buscar fichas técnicas por produto", description = "Retorna todas as fichas técnicas de um produto específico")
    public ResponseEntity<List<FichaTecnicaDTO>> findByProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(service.findByProduto(produtoId));
    }
}
