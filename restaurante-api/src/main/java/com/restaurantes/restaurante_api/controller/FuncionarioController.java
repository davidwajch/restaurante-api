package com.restaurantes.restaurante_api.controller;

import com.restaurantes.restaurante_api.dto.FuncionarioDTO;
import com.restaurantes.restaurante_api.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
@Tag(name = "Funcionários", description = "API para gerenciamento de funcionários")
public class FuncionarioController {
    
    @Autowired
    private FuncionarioService service;
    
    @GetMapping
    @Operation(summary = "Listar todos os funcionários", description = "Retorna uma lista com todos os funcionários cadastrados")
    public ResponseEntity<List<FuncionarioDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar funcionário por ID", description = "Retorna um funcionário específico pelo seu ID")
    public ResponseEntity<FuncionarioDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @PostMapping
    @Operation(summary = "Criar novo funcionário", description = "Cria um novo funcionário no sistema")
    public ResponseEntity<FuncionarioDTO> create(@RequestBody FuncionarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar funcionário", description = "Atualiza os dados de um funcionário existente")
    public ResponseEntity<FuncionarioDTO> update(@PathVariable Long id, @RequestBody FuncionarioDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir funcionário", description = "Remove um funcionário do sistema")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Buscar funcionários por status", description = "Retorna funcionários filtrados por status (ativo/inativo)")
    public ResponseEntity<List<FuncionarioDTO>> findByStatus(@PathVariable Boolean status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }
}
