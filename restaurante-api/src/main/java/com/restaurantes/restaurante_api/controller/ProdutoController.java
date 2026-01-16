package com.restaurantes.restaurante_api.controller;

import com.restaurantes.restaurante_api.dto.ProdutoDTO;
import com.restaurantes.restaurante_api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "API para gerenciamento de produtos do cardápio")
public class ProdutoController {
    
    @Autowired
    private ProdutoService service;
    
    @GetMapping
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista com todos os produtos do cardápio")
    public ResponseEntity<List<ProdutoDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo seu ID")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @PostMapping
    @Operation(summary = "Criar novo produto", description = "Cria um novo produto no cardápio")
    public ResponseEntity<ProdutoDTO> create(@RequestBody ProdutoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    public ResponseEntity<ProdutoDTO> update(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto", description = "Remove um produto do cardápio")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Buscar produtos por categoria", description = "Retorna produtos filtrados por categoria")
    public ResponseEntity<List<ProdutoDTO>> findByCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(service.findByCategoria(categoriaId));
    }
}
