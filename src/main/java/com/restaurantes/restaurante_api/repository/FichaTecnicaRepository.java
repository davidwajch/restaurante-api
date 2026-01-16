package com.restaurantes.restaurante_api.repository;

import com.restaurantes.restaurante_api.model.FichaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FichaTecnicaRepository extends JpaRepository<FichaTecnica, Long> {
    List<FichaTecnica> findByProdutoId(Long produtoId);
    List<FichaTecnica> findByInsumoId(Long insumoId);
}
