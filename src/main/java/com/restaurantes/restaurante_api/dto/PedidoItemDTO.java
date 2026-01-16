package com.restaurantes.restaurante_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItemDTO {
    private Long id;
    private Long pedidoId;
    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;
    private LocalDateTime dataCriacao;
}
