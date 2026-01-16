package com.restaurantes.restaurante_api.repository;

import com.restaurantes.restaurante_api.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findByStatus(Boolean status);
    List<Funcionario> findByCargo(String cargo);
}
