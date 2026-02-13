package com.restaurantes.restaurante_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoComFichaTecnicaRequest {

    @NotBlank(message = "Nome do produto é obrigatório")
    private String nome;

    @NotNull(message = "Preço de venda é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    private BigDecimal precoVenda;

    private Long categoriaId;

    @Valid
    private List<ItemFichaTecnicaRequest> itensFichaTecnica;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemFichaTecnicaRequest {
        @NotNull(message = "ID do insumo é obrigatório")
        private Long insumoId;

        @NotNull(message = "Quantidade necessária é obrigatória")
        @DecimalMin(value = "0.01", message = "Quantidade deve ser maior que zero")
        private BigDecimal quantidadeNecessaria;
    }
}
