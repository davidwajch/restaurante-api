package com.restaurantes.restaurante_api.service;

import com.restaurantes.restaurante_api.dto.EstoqueDTO;
import com.restaurantes.restaurante_api.model.Estoque;
import com.restaurantes.restaurante_api.model.Insumo;
import com.restaurantes.restaurante_api.repository.EstoqueRepository;
import com.restaurantes.restaurante_api.repository.InsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstoqueService {
    
    @Autowired
    private EstoqueRepository repository;
    
    @Autowired
    private InsumoRepository insumoRepository;
    
    public List<EstoqueDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public EstoqueDTO findById(Long id) {
        Estoque estoque = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
        return toDTO(estoque);
    }
    
    @Transactional
    public EstoqueDTO save(EstoqueDTO dto) {
        Insumo insumo = insumoRepository.findById(dto.getInsumoId())
                .orElseThrow(() -> new RuntimeException("Insumo não encontrado"));
        
        Estoque estoque = toEntity(dto, insumo);
        estoque.setUltimaAtualizacao(LocalDateTime.now());
        estoque = repository.save(estoque);
        return toDTO(estoque);
    }
    
    @Transactional
    public EstoqueDTO update(Long id, EstoqueDTO dto) {
        Estoque estoque = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
        
        Insumo insumo = insumoRepository.findById(dto.getInsumoId())
                .orElseThrow(() -> new RuntimeException("Insumo não encontrado"));
        
        estoque.setInsumo(insumo);
        estoque.setQuantidadeAtual(dto.getQuantidadeAtual());
        estoque.setQuantidadeMinima(dto.getQuantidadeMinima());
        estoque.setDataValidade(dto.getDataValidade());
        estoque.setLote(dto.getLote());
        estoque.setUltimaAtualizacao(LocalDateTime.now());
        
        estoque = repository.save(estoque);
        return toDTO(estoque);
    }
    
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public List<EstoqueDTO> findVencidos() {
        return repository.findByDataValidadeBefore(java.time.LocalDate.now()).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<EstoqueDTO> findEstoqueBaixo() {
        return repository.findAll().stream()
                .filter(e -> e.getQuantidadeAtual().compareTo(e.getQuantidadeMinima()) <= 0)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    private EstoqueDTO toDTO(Estoque estoque) {
        EstoqueDTO dto = new EstoqueDTO();
        dto.setId(estoque.getId());
        dto.setInsumoId(estoque.getInsumo().getId());
        dto.setInsumoNome(estoque.getInsumo().getNome());
        dto.setQuantidadeAtual(estoque.getQuantidadeAtual());
        dto.setQuantidadeMinima(estoque.getQuantidadeMinima());
        dto.setDataValidade(estoque.getDataValidade());
        dto.setLote(estoque.getLote());
        dto.setUltimaAtualizacao(estoque.getUltimaAtualizacao());
        return dto;
    }
    
    private Estoque toEntity(EstoqueDTO dto, Insumo insumo) {
        Estoque estoque = new Estoque();
        estoque.setId(dto.getId());
        estoque.setInsumo(insumo);
        estoque.setQuantidadeAtual(dto.getQuantidadeAtual());
        estoque.setQuantidadeMinima(dto.getQuantidadeMinima());
        estoque.setDataValidade(dto.getDataValidade());
        estoque.setLote(dto.getLote());
        return estoque;
    }
}
