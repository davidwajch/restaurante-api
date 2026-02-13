package com.restaurantes.restaurante_api.service;

import com.restaurantes.restaurante_api.dto.*;
import com.restaurantes.restaurante_api.exception.ResourceNotFoundException;
import com.restaurantes.restaurante_api.model.Produto;
import com.restaurantes.restaurante_api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository repository;
    
    @Autowired
    private FichaTecnicaService fichaTecnicaService;
    
    public List<ProdutoDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public ProdutoDTO findById(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
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
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        
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
    
    @Transactional
    public ProdutoComFichaTecnicaResponse saveComFichaTecnica(ProdutoComFichaTecnicaRequest request) {
        ProdutoDTO produtoDTO = save(new ProdutoDTO(null, request.getNome(), request.getPrecoVenda(), request.getCategoriaId()));
        
        List<FichaTecnicaDTO> fichas = new ArrayList<>();
        if (request.getItensFichaTecnica() != null && !request.getItensFichaTecnica().isEmpty()) {
            for (ProdutoComFichaTecnicaRequest.ItemFichaTecnicaRequest item : request.getItensFichaTecnica()) {
                FichaTecnicaDTO fichaDto = new FichaTecnicaDTO();
                fichaDto.setProdutoId(produtoDTO.getId());
                fichaDto.setInsumoId(item.getInsumoId());
                fichaDto.setQuantidadeNecessaria(item.getQuantidadeNecessaria());
                fichas.add(fichaTecnicaService.save(fichaDto));
            }
        }
        return new ProdutoComFichaTecnicaResponse(produtoDTO, fichas);
    }
    
    public ProdutoComFichaTecnicaResponse findByIdComFichaTecnica(Long id) {
        ProdutoDTO produtoDTO = findById(id);
        List<FichaTecnicaDTO> fichas = fichaTecnicaService.findByProduto(id);
        return new ProdutoComFichaTecnicaResponse(produtoDTO, fichas);
    }
    
    @Transactional
    public ProdutoComFichaTecnicaResponse updateComFichaTecnica(Long id, ProdutoComFichaTecnicaRequest request) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        
        produto.setNome(request.getNome());
        produto.setPrecoVenda(request.getPrecoVenda());
        produto.setCategoriaId(request.getCategoriaId());
        produto = repository.save(produto);
        
        fichaTecnicaService.deleteByProdutoId(id);
        
        List<FichaTecnicaDTO> fichas = new ArrayList<>();
        if (request.getItensFichaTecnica() != null && !request.getItensFichaTecnica().isEmpty()) {
            for (ProdutoComFichaTecnicaRequest.ItemFichaTecnicaRequest item : request.getItensFichaTecnica()) {
                FichaTecnicaDTO fichaDto = new FichaTecnicaDTO();
                fichaDto.setProdutoId(produto.getId());
                fichaDto.setInsumoId(item.getInsumoId());
                fichaDto.setQuantidadeNecessaria(item.getQuantidadeNecessaria());
                fichas.add(fichaTecnicaService.save(fichaDto));
            }
        }
        return new ProdutoComFichaTecnicaResponse(toDTO(produto), fichas);
    }
    
    @Transactional
    public void deleteComFichaTecnica(Long id) {
        fichaTecnicaService.deleteByProdutoId(id);
        repository.deleteById(id);
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
