# Restaurante API

API REST em **Java 17** com **Spring Boot** para gestão de restaurante: produtos, pedidos, mesas, estoque, insumos, fichas técnicas e funcionários. Inclui integração com **PostgreSQL** e documentação via **Springdoc OpenAPI** (Swagger UI).

## Stack

- Spring Boot 4.x, Spring Data JPA, Hibernate
- PostgreSQL
- Docker Compose (banco de dados local)

## Pré-requisitos

- JDK 17+
- Maven
- Docker e Docker Compose (para subir o PostgreSQL)

## Configuração

1. Copie o arquivo de ambiente:

   ```bash
   copy .env.example .env
   ```

   No Linux/macOS: `cp .env.example .env`

2. Edite `.env` e defina **a mesma** senha em `POSTGRES_PASSWORD` e `SPRING_DATASOURCE_PASSWORD` (e demais variáveis, se necessário).

3. Suba o banco:

   ```bash
   docker compose up -d
   ```

   Detalhes adicionais: [README-DOCKER.md](README-DOCKER.md).

4. Execute a aplicação (as variáveis `SPRING_DATASOURCE_*` devem estar disponíveis no ambiente — por exemplo, carregando `.env` na IDE ou exportando no terminal):

   ```bash
   mvn spring-boot:run
   ```

A API sobe por padrão na porta **8081**. Acesse o Swagger em `http://localhost:8081/swagger-ui.html` (caminho exato pode variar conforme a versão do Springdoc).

## Segurança

Não commite o arquivo `.env`. Se este repositório já expôs credenciais no passado, **troque a senha** no banco e em todo lugar onde ainda estiver em uso.

## Licença

Veja [LICENSE](LICENSE).
