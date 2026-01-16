package com.restaurantes.restaurante_api.repository;

import com.restaurantes.restaurante_api.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
}
