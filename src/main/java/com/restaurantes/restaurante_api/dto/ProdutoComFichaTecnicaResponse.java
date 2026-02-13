package com.restaurantes.restaurante_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoComFichaTecnicaResponse {
    private ProdutoDTO produto;
    private List<FichaTecnicaDTO> fichaTecnica;
}
