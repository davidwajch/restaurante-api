package com.restaurantes.restaurante_api.controller;

import com.restaurantes.restaurante_api.dto.PedidoDTO;
import com.restaurantes.restaurante_api.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "API para gerenciamento de pedidos do restaurante")
public class PedidoController {
    
    @Autowired
    private PedidoService service;
    
    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista com todos os pedidos")
    public ResponseEntity<List<PedidoDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico pelo seu ID")
    public ResponseEntity<PedidoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @PostMapping
    @Operation(summary = "Criar novo pedido", description = "Cria um novo pedido no sistema com seus itens")
    public ResponseEntity<PedidoDTO> create(@RequestBody PedidoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
    
    @PutMapping("/{id}/fechar")
    @Operation(summary = "Fechar pedido", description = "Fecha um pedido e atribui o caixa responsável")
    public ResponseEntity<PedidoDTO> fecharPedido(@PathVariable Long id, @RequestParam Long caixaId) {
        return ResponseEntity.ok(service.fecharPedido(id, caixaId));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pedido", description = "Remove um pedido do sistema")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/mesa/{mesaId}")
    @Operation(summary = "Buscar pedidos por mesa", description = "Retorna todos os pedidos de uma mesa específica")
    public ResponseEntity<List<PedidoDTO>> findByMesa(@PathVariable Long mesaId) {
        return ResponseEntity.ok(service.findByMesa(mesaId));
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Buscar pedidos por status", description = "Retorna pedidos filtrados por status (0=Aberto, 1=Fechado, 2=Cancelado)")
    public ResponseEntity<List<PedidoDTO>> findByStatus(@PathVariable Integer status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }
}
