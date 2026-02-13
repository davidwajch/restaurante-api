package com.restaurantes.restaurante_api.repository;

import com.restaurantes.restaurante_api.model.FichaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FichaTecnicaRepository extends JpaRepository<FichaTecnica, Long> {
    List<FichaTecnica> findByProdutoId(Long produtoId);
    List<FichaTecnica> findByInsumoId(Long insumoId);

    @Modifying
    @Query("DELETE FROM FichaTecnica f WHERE f.produto.id = :produtoId")
    void deleteByProdutoId(@Param("produtoId") Long produtoId);
}
