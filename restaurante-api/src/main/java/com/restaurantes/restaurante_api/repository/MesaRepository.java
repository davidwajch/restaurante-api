package com.restaurantes.restaurante_api.repository;

import com.restaurantes.restaurante_api.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    Optional<Mesa> findByNumero(String numero);
    List<Mesa> findByStatus(Integer status);
}
