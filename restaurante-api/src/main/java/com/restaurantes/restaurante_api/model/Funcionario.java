package com.restaurantes.restaurante_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "funcionarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String cargo;
    
    @Column(name = "data_admissao", nullable = false)
    private LocalDate dataAdmissao;
    
    @Column(nullable = false)
    private Boolean status;
}
