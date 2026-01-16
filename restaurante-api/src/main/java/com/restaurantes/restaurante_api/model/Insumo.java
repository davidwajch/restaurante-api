package com.restaurantes.restaurante_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "insumos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Insumo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(name = "unidade_medida", nullable = false)
    private String unidadeMedida;
}
