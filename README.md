# Twitter API v2

API RESTful para gerenciar um Twitter simplificado, utilizando princípios de **Domain Driven Design**.

## Tecnologias
- Java 17
  - JavaDoc
- Spring Framework
    - SpringBoot
    - Spring Security
    - Spring OpenAPI (Swagger)
- Lombok
- JWT
- JUnit
- Mockito
- SQL
- Flyway
- PostgreSQL / H2 Database / Redis
- Docker/Docker compose

## Features

- [x] Autenticação
- [x] Gerenciamento de usuários
- [x] Seguir usuários
- [ ] Criar tweets
- [ ] Interagir com tweets (comentário/like)

## Ferramentas para executar

- Java 17
- Apache maven
- Docker

## Como executar

1. Clone o projeto

    ````bash
    git clone https://github.com/filipemartinsdev/twitter-api
    cd twitter-api
    ````

2. Crie e configure o arquivo .env

    ````
    cp .env.example .env
    ````

    ````dotenv
    JWT_SECRET=<YOUR_SECRET>
    JWT_ISSUER=<YOUR_ISSUER>
    JWT_EXPIRATION_SECONDS=<YOUR_EXPIRATION_SECONDS>
    
    DB_USERNAME=<YOUR_USERNAME>
    DB_PASSWORD=<YOUR_PASSWORD>
    DB_DATABASE=<YOUR_DATABASE>
    
    REDIS_PASSWORD=<YOUR_PASSWORD>
    ````

3. Crie o arquivo JAR

    ````bash
    mvn clean package
    ````

4. Inicie os containers

    ````bash
    docker compose up -d --build
    ````

## Endpoints
Endpoints de listagem permitem a passagem de parâmetros de paginação:
- **page**=int
- **size**=int
- **sort**=attribute

---

- POST `/api/v2/auth/register` <p>
    ````JSON
    {
      "username": "your_name",
      "email": "your_email@gmail.com",
      "password": "your_password"
    } 
    ````
- POST `/api/v2/auth/login` <p>
    ````JSON
    {
      "username": "your_username",
      "password": "your_password"
    }
    ````
- DELETE `/api/v2/users/me` <p>
- GET `/api/v2/users?q=<username>` <p>
- GET `/api/v2/users/me` <p>
- GET `/api/v2/users/{id}` <p>
- GET `/api/v2/users/{id}/tweets` <p>
- GET `/api/v2/users/{id}/followers` <p>
- GET `/api/v2/users/{id}/following` <p>
- POST `/api/v2/users/{id}/followers` <p>
- DELETE `/api/v2/users/{id}/followers` <p>
- GET `/api/v2/tweets` <p>
- POST `/api/v2/tweets` <p>
    ````JSON
    {
      "content": "YOUR TEXT",
      "parentId": <OPTIONAL>
    }
    ````
- DELETE `/api/v2/tweets/{id}` <p>
- POST `/api/v2/tweets/{id}/likes` <p>
- DELETE `/api/v2/tweets/{id}/likes` <p>

## To do list

- [x] Autenticação
  - [x] Login/Register
  - [x] Token JWT
  - [x] User roles
- [x] Database Migrations
- [ ] Testes unitários
- [ ] Documentação
  - [ ] API
  - [ ] Projeto
- [x] Cache (Redis)
- [x] Imagem Docker
- [x] Container configs (Docker compose)
- [ ] Deploy (Railway/Render)

