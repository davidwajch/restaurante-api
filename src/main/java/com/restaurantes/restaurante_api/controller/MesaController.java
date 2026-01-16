package com.restaurantes.restaurante_api.controller;

import com.restaurantes.restaurante_api.dto.MesaDTO;
import com.restaurantes.restaurante_api.service.MesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@Tag(name = "Mesas", description = "API para gerenciamento de mesas do restaurante")
public class MesaController {
    
    @Autowired
    private MesaService service;
    
    @GetMapping
    @Operation(summary = "Listar todas as mesas", description = "Retorna uma lista com todas as mesas cadastradas")
    public ResponseEntity<List<MesaDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar mesa por ID", description = "Retorna uma mesa específica pelo seu ID")
    public ResponseEntity<MesaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @PostMapping
    @Operation(summary = "Criar nova mesa", description = "Cria uma nova mesa no sistema")
    public ResponseEntity<MesaDTO> create(@RequestBody MesaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar mesa", description = "Atualiza os dados de uma mesa existente")
    public ResponseEntity<MesaDTO> update(@PathVariable Long id, @RequestBody MesaDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir mesa", description = "Remove uma mesa do sistema")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Buscar mesas por status", description = "Retorna mesas filtradas por status (0=Livre, 1=Ocupada, 2=Reservada)")
    public ResponseEntity<List<MesaDTO>> findByStatus(@PathVariable Integer status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }
}
