package com.restaurantes.restaurante_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {
    private Long id;
    private String nome;
    private String cargo;
    private LocalDate dataAdmissao;
    private Boolean status;
}
