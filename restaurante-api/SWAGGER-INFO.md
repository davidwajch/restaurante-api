# Documentação Swagger/OpenAPI

## Acesso à Documentação

Após iniciar a aplicação, acesse a documentação interativa do Swagger nos seguintes endereços:

### Swagger UI (Interface Interativa)
```
http://localhost:8081/swagger-ui.html
```
ou
```
http://localhost:8081/swagger-ui/index.html
```

### OpenAPI JSON (Especificação)
```
http://localhost:8081/v3/api-docs
```

### OpenAPI YAML (Especificação)
```
http://localhost:8081/v3/api-docs.yaml
```

## Funcionalidades

- **Visualização de todos os endpoints** da API
- **Teste interativo** - Execute requisições diretamente pelo navegador
- **Documentação completa** de todos os parâmetros e respostas
- **Schemas dos DTOs** - Veja a estrutura de todos os objetos

## Endpoints Documentados

A documentação inclui todos os seguintes módulos:

1. **Funcionários** - `/api/funcionarios`
2. **Mesas** - `/api/mesas`
3. **Produtos** - `/api/produtos`
4. **Insumos** - `/api/insumos`
5. **Estoque** - `/api/estoque`
6. **Pedidos** - `/api/pedidos`
7. **Ficha Técnica** - `/api/ficha-tecnica`

## Como Usar

1. Acesse `http://localhost:8081/swagger-ui.html`
2. Expanda o módulo desejado (ex: "Funcionários")
3. Clique em um endpoint para ver detalhes
4. Clique em "Try it out" para testar
5. Preencha os parâmetros e clique em "Execute"

## Configuração

A configuração do Swagger está em:
- `src/main/java/com/restaurantes/restaurante_api/config/OpenApiConfig.java`

Para personalizar, edite este arquivo.
