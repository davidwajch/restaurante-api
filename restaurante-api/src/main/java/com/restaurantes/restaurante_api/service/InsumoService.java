package com.restaurantes.restaurante_api.service;

import com.restaurantes.restaurante_api.dto.InsumoDTO;
import com.restaurantes.restaurante_api.model.Insumo;
import com.restaurantes.restaurante_api.repository.InsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsumoService {
    
    @Autowired
    private InsumoRepository repository;
    
    public List<InsumoDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public InsumoDTO findById(Long id) {
        Insumo insumo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo não encontrado"));
        return toDTO(insumo);
    }
    
    @Transactional
    public InsumoDTO save(InsumoDTO dto) {
        Insumo insumo = toEntity(dto);
        insumo = repository.save(insumo);
        return toDTO(insumo);
    }
    
    @Transactional
    public InsumoDTO update(Long id, InsumoDTO dto) {
        Insumo insumo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo não encontrado"));
        
        insumo.setNome(dto.getNome());
        insumo.setUnidadeMedida(dto.getUnidadeMedida());
        
        insumo = repository.save(insumo);
        return toDTO(insumo);
    }
    
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    private InsumoDTO toDTO(Insumo insumo) {
        return new InsumoDTO(
                insumo.getId(),
                insumo.getNome(),
                insumo.getUnidadeMedida()
        );
    }
    
    private Insumo toEntity(InsumoDTO dto) {
        Insumo insumo = new Insumo();
        insumo.setId(dto.getId());
        insumo.setNome(dto.getNome());
        insumo.setUnidadeMedida(dto.getUnidadeMedida());
        return insumo;
    }
}
