package com.restaurantes.restaurante_api.service;

import com.restaurantes.restaurante_api.dto.FichaTecnicaDTO;
import com.restaurantes.restaurante_api.model.FichaTecnica;
import com.restaurantes.restaurante_api.model.Produto;
import com.restaurantes.restaurante_api.model.Insumo;
import com.restaurantes.restaurante_api.repository.FichaTecnicaRepository;
import com.restaurantes.restaurante_api.repository.ProdutoRepository;
import com.restaurantes.restaurante_api.repository.InsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FichaTecnicaService {
    
    @Autowired
    private FichaTecnicaRepository repository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private InsumoRepository insumoRepository;
    
    public List<FichaTecnicaDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public FichaTecnicaDTO findById(Long id) {
        FichaTecnica ficha = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ficha técnica não encontrada"));
        return toDTO(ficha);
    }
    
    @Transactional
    public FichaTecnicaDTO save(FichaTecnicaDTO dto) {
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        
        Insumo insumo = insumoRepository.findById(dto.getInsumoId())
                .orElseThrow(() -> new RuntimeException("Insumo não encontrado"));
        
        FichaTecnica ficha = new FichaTecnica();
        ficha.setProduto(produto);
        ficha.setInsumo(insumo);
        ficha.setQuantidadeNecessaria(dto.getQuantidadeNecessaria());
        
        ficha = repository.save(ficha);
        return toDTO(ficha);
    }
    
    @Transactional
    public FichaTecnicaDTO update(Long id, FichaTecnicaDTO dto) {
        FichaTecnica ficha = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ficha técnica não encontrada"));
        
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        
        Insumo insumo = insumoRepository.findById(dto.getInsumoId())
                .orElseThrow(() -> new RuntimeException("Insumo não encontrado"));
        
        ficha.setProduto(produto);
        ficha.setInsumo(insumo);
        ficha.setQuantidadeNecessaria(dto.getQuantidadeNecessaria());
        
        ficha = repository.save(ficha);
        return toDTO(ficha);
    }
    
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public List<FichaTecnicaDTO> findByProduto(Long produtoId) {
        return repository.findByProdutoId(produtoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    private FichaTecnicaDTO toDTO(FichaTecnica ficha) {
        FichaTecnicaDTO dto = new FichaTecnicaDTO();
        dto.setId(ficha.getId());
        dto.setProdutoId(ficha.getProduto().getId());
        dto.setProdutoNome(ficha.getProduto().getNome());
        dto.setInsumoId(ficha.getInsumo().getId());
        dto.setInsumoNome(ficha.getInsumo().getNome());
        dto.setQuantidadeNecessaria(ficha.getQuantidadeNecessaria());
        return dto;
    }
}
