package com.restaurantes.restaurante_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueDTO {
    private Long id;
    private Long insumoId;
    private String insumoNome;
    private BigDecimal quantidadeAtual;
    private BigDecimal quantidadeMinima;
    private LocalDate dataValidade;
    private String lote;
    private LocalDateTime ultimaAtualizacao;
}
