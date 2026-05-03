# Docker — PostgreSQL (Restaurante API)

Use o arquivo **`.env`** na raiz do projeto (copie de `.env.example`). O `docker compose` lê `POSTGRES_USER`, `POSTGRES_PASSWORD` e `POSTGRES_DB` a partir dele.

## Opção 1: Docker Compose (recomendado)

### 1. Criar `.env`

```bash
copy .env.example .env
```

Edite `.env` e defina `POSTGRES_PASSWORD` (e alinhe `SPRING_DATASOURCE_PASSWORD` na mesma senha para rodar a API localmente).

### 2. Subir o banco

```bash
docker compose up -d
```

### 3. Verificar container

```bash
docker ps
```

### 4. Parar

```bash
docker compose down
```

### 5. Parar e apagar volumes (apaga dados)

```bash
docker compose down -v
```

### 6. Logs

```bash
docker compose logs -f postgres
```

## Opção 2: Docker run manual

Defina a senha no ambiente (PowerShell):

```powershell
$env:POSTGRES_PASSWORD = "sua-senha-aqui"
docker run --name restaurante-postgres `
  -e POSTGRES_USER=postgres `
  -e POSTGRES_PASSWORD=$env:POSTGRES_PASSWORD `
  -e POSTGRES_DB=restaurante_db `
  -p 5433:5432 `
  -d postgres:15-alpine
```

Ajuste `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD` ao rodar a aplicação Spring Boot.

## psql (opcional)

```bash
docker exec -it restaurante-postgres psql -U postgres -d restaurante_db
```

## Conexão da aplicação

A API usa `SPRING_DATASOURCE_*` definidos no ambiente ou na sua IDE. Não commite senhas em `application.properties`.
