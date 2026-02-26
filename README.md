<br>

<div align="center">
<img src="images/twitter-api.png" alt="Logo" width="160pt">

<h3 align="center">Twitter API v2</h3>    

<p>

API para gerenciar um mini twitter

**[Documentação »](https://twitterapi.up.railway.app/swagger-ui.html)**

</p>

</div>

> ⚙️ Em contrução 

## Sobre

Projeto desenvolvido seguindo princípios de **Domain Driven Design** e **Clean Architecture**.

## Tecnologias

- Java 17
  - JavaDoc
- Spring Framework
    - SpringBoot
    - Spring Security
    - Spring OpenAPI (Swagger)
    - Spring Modulith
- Lombok
- JWT
- JUnit
- Mockito
- SQL
- Flyway
- PostgreSQL / H2 Database / Redis
- Docker/Docker compose

````bash
     178 text files.
     172 unique files.                                          
      16 files ignored.

-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                           121            727             50           4434
XML                             14              0              0            604
Bourne Shell                     1             32             52            211
Markdown                         3             76              0            202
Maven                            1              8              0            175
DOS Batch                        1             24              0            165
YAML                             6             15              4            164
SQL                             21             15              0            107
HTML                             1             16              0             89
Dockerfile                       1              5              0             11
Properties                       1              0              0              3
Text                             1              0              0              3
-------------------------------------------------------------------------------
SUM:                           172            918            106           6168
-------------------------------------------------------------------------------
````

## Features

- [x] Autenticação
- [x] Seguir usuários
- [x] Criar tweet
- [x] Interagir com tweet (comentário/like/view)
- [x] Buscar usuários
- [x] Listar tweets
- [ ] Feed personalizado por usuário

## Como rodar localmente
1. Clone o projeto

    ````bash
    git clone https://github.com/filipemartinsdev/twitter-api
    cd twitter-api
    ````

2. Crie e configure o arquivo `.env`

    ````
    cp .env.example .env
    ````

    ````dotenv
    JWT_SECRET=<YOUR_SECRET>
    JWT_ISSUER=<YOUR_ISSUER>
    
    DB_URL=<YOUR_URL>
    DB_USERNAME=<YOUR_USERNAME>
    DB_PASSWORD=<YOUR_PASSWORD>
   
    REDIS_URL=<YOUR_URL>
    REDIS_PASSWORD=<YOUR_PASSWORD>
    ````

3. Inicie os containers

    ````bash
    docker compose -f docker-compose-dev.yaml up -d --build
    ````

## Requisitos funcionais
- Cadastro, Login e gerenciamento de usuários
- Criação e gerenciamento de posts (tweets)
- Interação entre usuário e tweet (like, comentário e visualização)
- Interação entre usuários (follow/unfollow)
- Busca de usuários
- Busca de tweets

## Requisitos não funcionais
- Tempo de resposta máximo de 1s (1000ms)
- Cache de pesquisas
- Response padronizado (JSend)
- Criptografia de senhas
- Autenticação com JWT
- Comportamento Stateless

## Arquitetura & Design
Projeto monolítico, seguindo princípios de:
- SOLID
- Domain Driven Design
- Clean Architecture

## Casos de uso

<img src="images/use_cases.png" height="567pt">

### Módulos
Os módulos seguem uma estrutura clara:

<img src="images/module.png" height="260pt">

Cada módulo possui três componentes padrões - application, domain e infrastructure.

<img src="images/module_components_infra.png" height="160pt">

<img src="images/module_components_app.png" height="160pt">

Seguindo esse padrão, têm-se os seguintes módulos:

<img src="images/packages.png" height="260pt">

### Database

A modelagem foi feita em um banco relacional.

<img src="images/database.png" height="460pt">

## To do list

- [x] Autenticação
  - [x] Login/Register
  - [x] Token JWT
  - [x] User roles
- [x] Database Migrations
- [x] Testes unitários (em progresso)
- [x] Documentação (em progresso)
  - [x] API 
  - [ ] Projeto
- [x] Cache (Redis)
- [x] Imagem Docker
- [x] Container configs (Docker compose)
- [x] Deploy (Railway)

---

_Made with ❤️ and ☕ by **Filipe Martins**_
