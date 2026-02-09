package com.restaurantes.restaurante_api.controller;

import com.restaurantes.restaurante_api.dto.EstoqueDTO;
import com.restaurantes.restaurante_api.service.EstoqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
@Tag(name = "Estoque", description = "API para gerenciamento de estoque e validade de insumos")
public class EstoqueController {
    
    @Autowired
    private EstoqueService service;
    
    @GetMapping
    @Operation(summary = "Listar todo o estoque", description = "Retorna uma lista com todo o estoque de insumos")
    public ResponseEntity<List<EstoqueDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar estoque por ID", description = "Retorna um registro de estoque específico pelo seu ID")
    public ResponseEntity<EstoqueDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @GetMapping("/insumo/{insumoId}")
    @Operation(summary = "Listar estoque por insumo", description = "Retorna todos os registros de estoque do insumo informado")
    public ResponseEntity<List<EstoqueDTO>> findByInsumo(@PathVariable Long insumoId) {
        return ResponseEntity.ok(service.findByInsumoId(insumoId));
    }
    
    @PostMapping
    @Operation(summary = "Criar novo registro de estoque", description = "Cria um novo registro de estoque para um insumo")
    public ResponseEntity<EstoqueDTO> create(@RequestBody EstoqueDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar estoque", description = "Atualiza os dados de um registro de estoque existente")
    public ResponseEntity<EstoqueDTO> update(@PathVariable Long id, @RequestBody EstoqueDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro de estoque", description = "Remove um registro de estoque do sistema")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/vencidos")
    @Operation(summary = "Buscar itens vencidos", description = "Retorna itens do estoque com data de validade vencida")
    public ResponseEntity<List<EstoqueDTO>> findVencidos() {
        return ResponseEntity.ok(service.findVencidos());
    }
    
    @GetMapping("/estoque-baixo")
    @Operation(summary = "Buscar estoque baixo", description = "Retorna itens do estoque com quantidade abaixo do mínimo")
    public ResponseEntity<List<EstoqueDTO>> findEstoqueBaixo() {
        return ResponseEntity.ok(service.findEstoqueBaixo());
    }
}
