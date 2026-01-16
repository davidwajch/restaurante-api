package com.restaurantes.restaurante_api.service;

import com.restaurantes.restaurante_api.dto.PedidoDTO;
import com.restaurantes.restaurante_api.dto.PedidoItemDTO;
import com.restaurantes.restaurante_api.model.*;
import com.restaurantes.restaurante_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository repository;
    
    @Autowired
    private MesaRepository mesaRepository;
    
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    public List<PedidoDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public PedidoDTO findById(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return toDTO(pedido);
    }
    
    @Transactional
    public PedidoDTO save(PedidoDTO dto) {
        Mesa mesa = mesaRepository.findById(dto.getMesaId())
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));
        
        Funcionario garcom = funcionarioRepository.findById(dto.getGarcomId())
                .orElseThrow(() -> new RuntimeException("Garçom não encontrado"));
        
        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setGarcom(garcom);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setQtdPessoas(dto.getQtdPessoas());
        pedido.setStatus(0); // Aberto
        pedido.setTotal(BigDecimal.ZERO);
        pedido.setItens(new ArrayList<>());
        
        // Calcular total dos itens
        BigDecimal total = BigDecimal.ZERO;
        if (dto.getItens() != null && !dto.getItens().isEmpty()) {
            for (PedidoItemDTO itemDTO : dto.getItens()) {
                Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
                
                PedidoItem item = new PedidoItem();
                item.setPedido(pedido);
                item.setProduto(produto);
                item.setQuantidade(itemDTO.getQuantidade());
                item.setDataCriacao(LocalDateTime.now());
                
                pedido.getItens().add(item);
                
                BigDecimal itemTotal = produto.getPrecoVenda()
                        .multiply(BigDecimal.valueOf(itemDTO.getQuantidade()));
                total = total.add(itemTotal);
            }
        }
        
        pedido.setTotal(total);
        pedido = repository.save(pedido);
        return toDTO(pedido);
    }
    
    @Transactional
    public PedidoDTO fecharPedido(Long id, Long caixaId) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        Funcionario caixa = funcionarioRepository.findById(caixaId)
                .orElseThrow(() -> new RuntimeException("Caixa não encontrado"));
        
        pedido.setCaixa(caixa);
        pedido.setStatus(1); // Fechado
        pedido = repository.save(pedido);
        return toDTO(pedido);
    }
    
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public List<PedidoDTO> findByMesa(Long mesaId) {
        return repository.findByMesaId(mesaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<PedidoDTO> findByStatus(Integer status) {
        return repository.findByStatus(status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    private PedidoDTO toDTO(Pedido pedido) {
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
        
        if (pedido.getItens() != null) {
            dto.setItens(pedido.getItens().stream()
                    .map(item -> {
                        PedidoItemDTO itemDTO = new PedidoItemDTO();
                        itemDTO.setId(item.getId());
                        itemDTO.setPedidoId(item.getPedido().getId());
                        itemDTO.setProdutoId(item.getProduto().getId());
                        itemDTO.setProdutoNome(item.getProduto().getNome());
                        itemDTO.setQuantidade(item.getQuantidade());
                        itemDTO.setDataCriacao(item.getDataCriacao());
                        return itemDTO;
                    })
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
}
