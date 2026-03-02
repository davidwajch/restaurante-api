package com.restaurantes.restaurante_api.service;

import com.restaurantes.restaurante_api.dto.PedidoDTO;
import com.restaurantes.restaurante_api.dto.PedidoItemDTO;
import com.restaurantes.restaurante_api.model.Pedido;
import com.restaurantes.restaurante_api.model.PedidoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Converte entidade Pedido em DTOs. Classe separada para evitar problemas de parsing no PedidoService.
 */
public final class PedidoMapper {

    private PedidoMapper() {
    }

    public static PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setMesaId(pedido.getMesa().getId());
        dto.setMesaNumero(pedido.getMesa().getNumero());
        dto.setGarcomId(pedido.getGarcom().getId());
        dto.setGarcomNome(pedido.getGarcom().getNome());
        if (pedido.getCaixa() != null) {
            dto.setCaixaId(pedido.getCaixa().getId());
            dto.setCaixaNome(pedido.getCaixa().getNome());
        }
        dto.setDataCriacao(pedido.getDataCriacao());
        dto.setQtdPessoas(pedido.getQtdPessoas());
        dto.setStatus(pedido.getStatus());
        dto.setTotal(pedido.getTotal());
        dto.setItens(pedido.getItens() == null ? new ArrayList<>() : toDTOItens(pedido.getItens()));
        return dto;
    }

    private static List<PedidoItemDTO> toDTOItens(List<PedidoItem> itens) {
        List<PedidoItemDTO> lista = new ArrayList<>();
        for (PedidoItem pi : itens) {
            PedidoItemDTO itemDTO = new PedidoItemDTO();
            itemDTO.setId(pi.getId());
            itemDTO.setPedidoId(pi.getPedido().getId());
            itemDTO.setProdutoId(pi.getProduto().getId());
            itemDTO.setProdutoNome(pi.getProduto().getNome());
            itemDTO.setQuantidade(pi.getQuantidade());
            itemDTO.setObservacao(pi.getObservacao());
            itemDTO.setDataCriacao(pi.getDataCriacao());
            lista.add(itemDTO);
        }
        return lista;
    }
}
