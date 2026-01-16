package com.restaurantes.restaurante_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "ficha_tecnica")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FichaTecnica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
    
    @ManyToOne
    @JoinColumn(name = "insumo_id", nullable = false)
    private Insumo insumo;
    
    @Column(name = "quantidade_necessaria", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantidadeNecessaria;
}
