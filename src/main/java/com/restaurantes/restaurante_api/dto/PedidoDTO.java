package com.restaurantes.restaurante_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private Long mesaId;
    private String mesaNumero;
    private Long garcomId;
    private String garcomNome;
    private Long caixaId;
    private String caixaNome;
    private LocalDateTime dataCriacao;
    private Integer qtdPessoas;
    private Integer status;
    private BigDecimal total;
    private List<PedidoItemDTO> itens;
}
