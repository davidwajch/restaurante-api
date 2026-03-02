package com.restaurantes.restaurante_api.service;

import com.restaurantes.restaurante_api.dto.AdicionarItemPedidoRequest;
import com.restaurantes.restaurante_api.dto.HistoricoPedidosResumoDTO;
import com.restaurantes.restaurante_api.dto.PedidoDTO;
import com.restaurantes.restaurante_api.dto.PedidoItemDTO;
import com.restaurantes.restaurante_api.model.*;
import com.restaurantes.restaurante_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO findById(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return PedidoMapper.toDTO(pedido);
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

        BigDecimal total = BigDecimal.ZERO;
        if (dto.getItens() != null && !dto.getItens().isEmpty()) {
            for (PedidoItemDTO itemDTO : dto.getItens()) {
                Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

                PedidoItem item = new PedidoItem();
                item.setPedido(pedido);
                item.setProduto(produto);
                item.setQuantidade(itemDTO.getQuantidade());
                item.setObservacao(itemDTO.getObservacao());
                item.setDataCriacao(LocalDateTime.now());

                pedido.getItens().add(item);

                BigDecimal itemTotal = produto.getPrecoVenda()
                        .multiply(BigDecimal.valueOf(itemDTO.getQuantidade()));
                total = total.add(itemTotal);
            }
        }

        pedido.setTotal(total);
        pedido = repository.save(pedido);
        return PedidoMapper.toDTO(pedido);
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
        return PedidoMapper.toDTO(pedido);
    }

    @Transactional
    public PedidoDTO cancelarPedido(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        if (pedido.getStatus() != 0) {
            throw new RuntimeException("Somente pedidos abertos podem ser cancelados");
        }
        pedido.setStatus(2); // Cancelado
        pedido = repository.save(pedido);
        return PedidoMapper.toDTO(pedido);
    }

    @Transactional
    public PedidoDTO removerItem(Long pedidoId, Long itemId) {
        Pedido pedido = repository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        if (pedido.getStatus() != 0) {
            throw new RuntimeException("Somente é possível remover itens de pedidos abertos");
        }
        if (pedido.getItens() == null) {
            throw new RuntimeException("Pedido não possui itens");
        }
        PedidoItem item = pedido.getItens().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item não encontrado no pedido"));
        BigDecimal valorItem = item.getProduto().getPrecoVenda()
                .multiply(BigDecimal.valueOf(item.getQuantidade()));
        pedido.getItens().remove(item);
        pedido.setTotal(pedido.getTotal().subtract(valorItem));
        pedido = repository.save(pedido);
        return PedidoMapper.toDTO(pedido);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /** Retorna apenas pedidos abertos (status 0) da mesa. */
    public List<PedidoDTO> findByMesa(Long mesaId) {
        return repository.findByMesaIdAndStatus(mesaId, 0).stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> findByStatus(Integer status) {
        return repository.findByStatus(status).stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> findHistoricoFechados(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(LocalTime.MAX);
        return repository.findByStatusAndDataCriacaoBetween(1, inicio, fim).stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public HistoricoPedidosResumoDTO getResumoContabilidade(LocalDate dataInicio, LocalDate dataFim) {
        List<PedidoDTO> pedidos = findHistoricoFechados(dataInicio, dataFim);
        BigDecimal valorTotal = pedidos.stream()
                .map(PedidoDTO::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new HistoricoPedidosResumoDTO(
                dataInicio,
                dataFim,
                pedidos.size(),
                valorTotal,
                pedidos
        );
    }

    @Transactional
    public PedidoDTO adicionarItem(Long pedidoId, AdicionarItemPedidoRequest request) {
        Pedido pedido = repository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        if (pedido.getStatus() != 0) {
            throw new RuntimeException("Somente é possível adicionar itens a pedidos abertos");
        }
        Produto produto = produtoRepository.findById(request.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        if (request.getQuantidade() == null || request.getQuantidade() <= 0) {
            throw new RuntimeException("Quantidade deve ser maior que zero");
        }
        if (pedido.getItens() == null) {
            pedido.setItens(new ArrayList<>());
        }
        PedidoItem novoItem = new PedidoItem();
        novoItem.setPedido(pedido);
        novoItem.setProduto(produto);
        novoItem.setQuantidade(request.getQuantidade());
        novoItem.setObservacao(request.getObservacao());
        novoItem.setDataCriacao(LocalDateTime.now());
        pedido.getItens().add(novoItem);
        BigDecimal itemTotal = produto.getPrecoVenda().multiply(BigDecimal.valueOf(request.getQuantidade()));
        pedido.setTotal(pedido.getTotal().add(itemTotal));
        pedido = repository.save(pedido);
        return PedidoMapper.toDTO(pedido);
    }
}
