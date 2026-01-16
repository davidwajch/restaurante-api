package com.restaurantes.restaurante_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;
    
    @ManyToOne
    @JoinColumn(name = "garcom_id", nullable = false)
    private Funcionario garcom;
    
    @ManyToOne
    @JoinColumn(name = "caixa_id")
    private Funcionario caixa;
    
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
    
    @Column(name = "qtd_pessoas")
    private Integer qtdPessoas;
    
    @Column(nullable = false)
    private Integer status; // 0 = Aberto, 1 = Fechado, 2 = Cancelado
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItem> itens;
}
