package com.restaurantes.restaurante_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "estoque")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "insumo_id", nullable = false)
    private Insumo insumo;
    
    @Column(name = "quantidade_atual", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantidadeAtual;
    
    @Column(name = "quantidade_minima", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantidadeMinima;
    
    @Column(name = "data_validade")
    private LocalDate dataValidade;
    
    @Column
    private String lote;
    
    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;
}
