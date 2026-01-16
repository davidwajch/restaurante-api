package com.restaurantes.restaurante_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Long id;
    private String nome;
    private BigDecimal precoVenda;
    private Long categoriaId;
}
