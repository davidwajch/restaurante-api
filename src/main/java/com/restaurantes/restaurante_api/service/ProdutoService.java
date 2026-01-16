package com.restaurantes.restaurante_api.service;

import com.restaurantes.restaurante_api.dto.ProdutoDTO;
import com.restaurantes.restaurante_api.model.Produto;
import com.restaurantes.restaurante_api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository repository;
    
    public List<ProdutoDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public ProdutoDTO findById(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return toDTO(produto);
    }
    
    @Transactional
    public ProdutoDTO save(ProdutoDTO dto) {
        Produto produto = toEntity(dto);
        produto = repository.save(produto);
        return toDTO(produto);
    }
    
    @Transactional
    public ProdutoDTO update(Long id, ProdutoDTO dto) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        
        produto.setNome(dto.getNome());
        produto.setPrecoVenda(dto.getPrecoVenda());
        produto.setCategoriaId(dto.getCategoriaId());
        
        produto = repository.save(produto);
        return toDTO(produto);
    }
    
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public List<ProdutoDTO> findByCategoria(Long categoriaId) {
        return repository.findByCategoriaId(categoriaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    private ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPrecoVenda(),
                produto.getCategoriaId()
        );
    }
    
    private Produto toEntity(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setId(dto.getId());
        produto.setNome(dto.getNome());
        produto.setPrecoVenda(dto.getPrecoVenda());
        produto.setCategoriaId(dto.getCategoriaId());
        return produto;
    }
}
