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
- Flyway
- PostgreSQL
- H2 Database
- Redis
- Docker/Docker compose

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

- POST `/api/v2/auth/register`
    ````JSON
    {
      "username": "your_name",
      "email": "your_email@gmail.com",
      "password": "your_password"
    } 
    ````
- POST `/api/v2/auth/login`
    ````JSON
    {
      "username": "your_username",
      "password": "your_password"
    }
    ````
- GET `/api/v2/users`
- GET `/api/v2/users/{id}` 
- GET `/api/v2/users/me` 

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

