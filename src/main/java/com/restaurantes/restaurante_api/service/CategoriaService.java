package com.restaurantes.restaurante_api.service;

import com.restaurantes.restaurante_api.dto.CategoriaDTO;
import com.restaurantes.restaurante_api.exception.ResourceNotFoundException;
import com.restaurantes.restaurante_api.model.Categoria;
import com.restaurantes.restaurante_api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<CategoriaDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO findById(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        return toDTO(categoria);
    }

    @Transactional
    public CategoriaDTO save(CategoriaDTO dto) {
        Categoria categoria = toEntity(dto);
        categoria.setId(null); // sempre insert no POST
        categoria = repository.save(categoria);
        return toDTO(categoria);
    }

    @Transactional
    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        categoria.setNome(dto.getNome());

        categoria = repository.save(categoria);
        return toDTO(categoria);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private CategoriaDTO toDTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNome()
        );
    }

    private Categoria toEntity(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setId(dto.getId());
        categoria.setNome(dto.getNome());
        return categoria;
    }
}
