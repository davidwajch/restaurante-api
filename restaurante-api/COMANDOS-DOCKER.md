# Comandos Docker Diretos

## 1. Criar e iniciar o container PostgreSQL
```bash
docker run --name restaurante-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=Restaurante@2024 -e POSTGRES_DB=restaurante_db -p 5433:5432 -d postgres:15-alpine
```

## 2. Verificar se o container está rodando
```bash
docker ps
```

## 3. Ver logs do container
```bash
docker logs restaurante-postgres
```

## 4. Ver logs em tempo real
```bash
docker logs -f restaurante-postgres
```

## 5. Parar o container
```bash
docker stop restaurante-postgres
```

## 6. Iniciar o container novamente
```bash
docker start restaurante-postgres
```

## 7. Reiniciar o container
```bash
docker restart restaurante-postgres
```

## 8. Remover o container (após parar)
```bash
docker rm restaurante-postgres
```

## 9. Parar e remover o container de uma vez
```bash
docker rm -f restaurante-postgres
```

## 10. Acessar o banco via psql (terminal interativo)
```bash
docker exec -it restaurante-postgres psql -U postgres -d restaurante_db
```

## 11. Executar comando SQL diretamente
```bash
docker exec -it restaurante-postgres psql -U postgres -d restaurante_db -c "SELECT version();"
```

## 12. Listar todos os containers (incluindo parados)
```bash
docker ps -a
```

## 13. Ver informações do container
```bash
docker inspect restaurante-postgres
```

## 14. Ver uso de recursos do container
```bash
docker stats restaurante-postgres
```
