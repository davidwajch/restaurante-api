package com.restaurantes.restaurante_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FichaTecnicaDTO {
    private Long id;
    private Long produtoId;
    private String produtoNome;
    private Long insumoId;
    private String insumoNome;
    private BigDecimal quantidadeNecessaria;
}
