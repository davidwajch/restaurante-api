package com.restaurantes.restaurante_api.service;

import com.restaurantes.restaurante_api.dto.MesaDTO;
import com.restaurantes.restaurante_api.model.Mesa;
import com.restaurantes.restaurante_api.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MesaService {
    
    @Autowired
    private MesaRepository repository;
    
    public List<MesaDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public MesaDTO findById(Long id) {
        Mesa mesa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));
        return toDTO(mesa);
    }
    
    @Transactional
    public MesaDTO save(MesaDTO dto) {
        Mesa mesa = toEntity(dto);
        mesa = repository.save(mesa);
        return toDTO(mesa);
    }
    
    @Transactional
    public MesaDTO update(Long id, MesaDTO dto) {
        Mesa mesa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));
        
        mesa.setNumero(dto.getNumero());
        mesa.setStatus(dto.getStatus());
        
        mesa = repository.save(mesa);
        return toDTO(mesa);
    }
    
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public List<MesaDTO> findByStatus(Integer status) {
        return repository.findByStatus(status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    private MesaDTO toDTO(Mesa mesa) {
        return new MesaDTO(
                mesa.getId(),
                mesa.getNumero(),
                mesa.getStatus()
        );
    }
    
    private Mesa toEntity(MesaDTO dto) {
        Mesa mesa = new Mesa();
        mesa.setId(dto.getId());
        mesa.setNumero(dto.getNumero());
        mesa.setStatus(dto.getStatus());
        return mesa;
    }
}
