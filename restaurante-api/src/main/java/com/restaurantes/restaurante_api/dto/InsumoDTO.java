package com.restaurantes.restaurante_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsumoDTO {
    private Long id;
    private String nome;
    private String unidadeMedida;
}
