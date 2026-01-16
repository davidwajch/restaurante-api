package com.restaurantes.restaurante_api.repository;

import com.restaurantes.restaurante_api.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByInsumoId(Long insumoId);
    List<Estoque> findByDataValidadeBefore(LocalDate data);
    List<Estoque> findByQuantidadeAtualLessThanEqual(BigDecimal quantidade);
}
