package com.restaurantes.restaurante_api.service;

import com.restaurantes.restaurante_api.dto.FuncionarioDTO;
import com.restaurantes.restaurante_api.model.Funcionario;
import com.restaurantes.restaurante_api.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {
    
    @Autowired
    private FuncionarioRepository repository;
    
    public List<FuncionarioDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public FuncionarioDTO findById(Long id) {
        Funcionario funcionario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        return toDTO(funcionario);
    }
    
    @Transactional
    public FuncionarioDTO save(FuncionarioDTO dto) {
        Funcionario funcionario = toEntity(dto);
        funcionario = repository.save(funcionario);
        return toDTO(funcionario);
    }
    
    @Transactional
    public FuncionarioDTO update(Long id, FuncionarioDTO dto) {
        Funcionario funcionario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        
        funcionario.setNome(dto.getNome());
        funcionario.setCargo(dto.getCargo());
        funcionario.setDataAdmissao(dto.getDataAdmissao());
        funcionario.setStatus(dto.getStatus());
        
        funcionario = repository.save(funcionario);
        return toDTO(funcionario);
    }
    
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public List<FuncionarioDTO> findByStatus(Boolean status) {
        return repository.findByStatus(status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    private FuncionarioDTO toDTO(Funcionario funcionario) {
        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCargo(),
                funcionario.getDataAdmissao(),
                funcionario.getStatus()
        );
    }
    
    private Funcionario toEntity(FuncionarioDTO dto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(dto.getId());
        funcionario.setNome(dto.getNome());
        funcionario.setCargo(dto.getCargo());
        funcionario.setDataAdmissao(dto.getDataAdmissao());
        funcionario.setStatus(dto.getStatus());
        return funcionario;
    }
}
