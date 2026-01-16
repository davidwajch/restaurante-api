package com.restaurantes.restaurante_api.controller;

import com.restaurantes.restaurante_api.dto.InsumoDTO;
import com.restaurantes.restaurante_api.service.InsumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos")
@Tag(name = "Insumos", description = "API para gerenciamento de insumos/ingredientes")
public class InsumoController {
    
    @Autowired
    private InsumoService service;
    
    @GetMapping
    @Operation(summary = "Listar todos os insumos", description = "Retorna uma lista com todos os insumos cadastrados")
    public ResponseEntity<List<InsumoDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar insumo por ID", description = "Retorna um insumo específico pelo seu ID")
    public ResponseEntity<InsumoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @PostMapping
    @Operation(summary = "Criar novo insumo", description = "Cria um novo insumo no sistema")
    public ResponseEntity<InsumoDTO> create(@RequestBody InsumoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar insumo", description = "Atualiza os dados de um insumo existente")
    public ResponseEntity<InsumoDTO> update(@PathVariable Long id, @RequestBody InsumoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir insumo", description = "Remove um insumo do sistema")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
