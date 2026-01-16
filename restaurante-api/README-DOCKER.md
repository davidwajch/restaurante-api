# Comandos Docker para Banco de Dados

## Opção 1: Usando Docker Compose (Recomendado)

### 1. Iniciar o banco de dados
```bash
docker-compose up -d
```

### 2. Verificar se o container está rodando
```bash
docker ps
```

### 3. Parar o banco de dados
```bash
docker-compose down
```

### 4. Parar e remover volumes (apaga os dados)
```bash
docker-compose down -v
```

### 5. Ver logs do container
```bash
docker-compose logs -f postgres
```

## Opção 2: Usando Docker diretamente

### 1. Criar e iniciar o container PostgreSQL
```bash
docker run --name restaurante-postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=Restaurante@2024 \
  -e POSTGRES_DB=restaurante_db \
  -p 5433:5432 \
  -d postgres:15-alpine
```

### 2. Verificar se o container está rodando
```bash
docker ps
```

### 3. Parar o container
```bash
docker stop restaurante-postgres
```

### 4. Iniciar o container novamente
```bash
docker start restaurante-postgres
```

### 5. Remover o container
```bash
docker rm -f restaurante-postgres
```

### 6. Acessar o banco via psql (opcional)
```bash
docker exec -it restaurante-postgres psql -U postgres -d restaurante_db
```

## Configuração

O banco de dados será criado automaticamente com:
- **Host:** localhost
- **Porta:** 5433
- **Usuário:** postgres
- **Senha:** Restaurante@2024
- **Database:** restaurante_db

Essas configurações já estão no arquivo `application.properties`.

## Verificar conexão

Após iniciar o container, você pode testar a conexão executando a aplicação Spring Boot. As tabelas serão criadas automaticamente pelo Hibernate.
