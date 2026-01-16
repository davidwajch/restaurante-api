package com.restaurantes.restaurante_api.repository;

import com.restaurantes.restaurante_api.model.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    List<PedidoItem> findByPedidoId(Long pedidoId);
    List<PedidoItem> findByProdutoId(Long produtoId);
}
