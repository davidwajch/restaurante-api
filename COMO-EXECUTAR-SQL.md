# Como Executar o Script SQL

## Opção 1: Via Docker (psql)

### 1. Copiar o script para o container
```bash
docker cp script-criar-tabelas.sql restaurante-postgres:/tmp/
```

### 2. Executar o script
```bash
docker exec -i restaurante-postgres psql -U postgres -d restaurante_db < script-criar-tabelas.sql
```

## Opção 2: Via psql interativo

### 1. Acessar o banco
```bash
docker exec -it restaurante-postgres psql -U postgres -d restaurante_db
```

### 2. Copiar e colar o conteúdo do arquivo `script-criar-tabelas.sql`

Ou executar diretamente:
```bash
docker exec -i restaurante-postgres psql -U postgres -d restaurante_db -f /tmp/script-criar-tabelas.sql
```

## Opção 3: Via arquivo local (PowerShell)

```powershell
Get-Content script-criar-tabelas.sql | docker exec -i restaurante-postgres psql -U postgres -d restaurante_db
```

## Verificar se as tabelas foram criadas

Após executar o script, verifique com:

```bash
docker exec -it restaurante-postgres psql -U postgres -d restaurante_db -c "\dt"
```

Você deve ver as 8 tabelas:
- funcionarios
- mesas
- insumos
- produtos
- pedidos
- pedido_itens
- ficha_tecnica
- estoque
