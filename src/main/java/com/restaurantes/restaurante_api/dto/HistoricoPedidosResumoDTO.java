package com.restaurantes.restaurante_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO de resumo do histórico de pedidos para contabilidade.
 * Agrupa totais e lista de pedidos fechados em um período.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoPedidosResumoDTO {
    private LocalDate periodoInicio;
    private LocalDate periodoFim;
    private Integer quantidadePedidos;
    private BigDecimal valorTotal;
    private List<PedidoDTO> pedidos;
}
