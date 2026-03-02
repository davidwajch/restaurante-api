package com.restaurantes.restaurante_api.controller;

import com.restaurantes.restaurante_api.dto.AdicionarItemPedidoRequest;
import com.restaurantes.restaurante_api.dto.HistoricoPedidosResumoDTO;
import com.restaurantes.restaurante_api.dto.PedidoDTO;
import com.restaurantes.restaurante_api.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    
    @PostMapping("/{id}/itens")
    @Operation(summary = "Adicionar item ao pedido", description = "Adiciona um produto ao pedido com a quantidade informada. Retorna o pedido atualizado com a lista de itens.")
    public ResponseEntity<PedidoDTO> adicionarItem(@PathVariable Long id, @RequestBody AdicionarItemPedidoRequest request) {
        return ResponseEntity.ok(service.adicionarItem(id, request));
    }
    
    @PutMapping("/{id}/fechar")
    @Operation(summary = "Fechar pedido", description = "Fecha um pedido e atribui o caixa responsável")
    public ResponseEntity<PedidoDTO> fecharPedido(@PathVariable Long id, @RequestParam Long caixaId) {
        return ResponseEntity.ok(service.fecharPedido(id, caixaId));
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pedido", description = "Marca o pedido como cancelado (status 2). Somente pedidos abertos podem ser cancelados. Mantém o registro para contabilidade.")
    public ResponseEntity<PedidoDTO> cancelarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelarPedido(id));
    }

    @DeleteMapping("/{pedidoId}/itens/{itemId}")
    @Operation(summary = "Remover item do pedido", description = "Remove um item do pedido e recalcula o total. Somente em pedidos abertos.")
    public ResponseEntity<PedidoDTO> removerItem(@PathVariable Long pedidoId, @PathVariable Long itemId) {
        return ResponseEntity.ok(service.removerItem(pedidoId, itemId));
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

    @GetMapping("/historico")
    @Operation(
            summary = "Histórico de pedidos fechados",
            description = "Retorna a lista de pedidos fechados no período (para contabilidade). Parâmetros: dataInicio e dataFim no formato yyyy-MM-dd."
    )
    public ResponseEntity<List<PedidoDTO>> findHistorico(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return ResponseEntity.ok(service.findHistoricoFechados(dataInicio, dataFim));
    }

    @GetMapping("/historico/resumo")
    @Operation(
            summary = "Resumo contábil do histórico",
            description = "Retorna resumo do período: quantidade de pedidos fechados, valor total e lista detalhada (para contabilidade)."
    )
    public ResponseEntity<HistoricoPedidosResumoDTO> getResumoContabilidade(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return ResponseEntity.ok(service.getResumoContabilidade(dataInicio, dataFim));
    }
}
