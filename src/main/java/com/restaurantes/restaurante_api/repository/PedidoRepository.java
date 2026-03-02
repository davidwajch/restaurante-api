package com.restaurantes.restaurante_api.repository;

import com.restaurantes.restaurante_api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByMesaId(Long mesaId);
    List<Pedido> findByMesaIdAndStatus(Long mesaId, Integer status);
    List<Pedido> findByStatus(Integer status);
    List<Pedido> findByGarcomId(Long garcomId);
    List<Pedido> findByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Pedido> findByStatusAndDataCriacaoBetween(Integer status, LocalDateTime inicio, LocalDateTime fim);
}
