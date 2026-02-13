package com.restaurantes.restaurante_api.controller;

import com.restaurantes.restaurante_api.dto.ProdutoComFichaTecnicaRequest;
import com.restaurantes.restaurante_api.dto.ProdutoComFichaTecnicaResponse;
import com.restaurantes.restaurante_api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produto-ficha-tecnica")
@Tag(name = "Produto com Ficha Técnica", description = "API para produto e ficha técnica em uma única operação")
public class ProdutoComFichaTecnicaController {

    @Autowired
    private ProdutoService service;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto com ficha técnica", description = "Retorna um produto e todas as suas fichas técnicas (insumos e quantidades)")
    public ResponseEntity<ProdutoComFichaTecnicaResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByIdComFichaTecnica(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto com ficha técnica", description = "Atualiza o produto e substitui todas as fichas técnicas pelos itens enviados")
    public ResponseEntity<ProdutoComFichaTecnicaResponse> update(@PathVariable Long id, @Valid @RequestBody ProdutoComFichaTecnicaRequest request) {
        return ResponseEntity.ok(service.updateComFichaTecnica(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto e ficha técnica", description = "Remove o produto e todas as suas fichas técnicas")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteComFichaTecnica(id);
        return ResponseEntity.noContent().build();
    }
}
