# Restaurant reservation API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Oracle](https://img.shields.io/badge/oracle-%23F80000.svg?style=for-the-badge&logo=oracle&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

This project is an API built using **Java, Java Spring, Flyway Migrations, Oracle as the database, and Spring Security and JWT for authentication control.**

This API was developed as a solution to a technical challenge found online. Its main goal is to manage restaurant table reservations efficiently and in an organized manner. [Link to challenge](https://racoelho.com.br/listas/desafios/sistema-de-reservas-de-restaurante)

## Table of Contents

- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)

## Installation

1. Clone the repository:

```bash
git clone https://github.com/luka-martins/reserve_adim.git
```
2. Install dependencies with Maven
3. Install [Oracle](https://www.oracle.com/database/technologies/oracle-database-software-downloads.html)
4. Use the file <strong> Create_db.sql </strong> to create the database. [Link to the file](src/main/resources/db/Create_db.sql)

## Usage

1. Start the application with Maven
2. The API will be accessible at http://localhost:8080

## API Endpoints
The API provides the following endpoints:

```markdown
POST /usuarios/registrar — Cadastro de novos usuários.

POST /usuarios/login — Login de usuários e geração de token JWT.

GET /mesas — Lista todas as mesas e seus status.

POST /mesas — Adiciona uma nova mesa (apenas administradores).

PATCH /mesas/:id — Atualiza informações de uma mesa.

DELETE /mesas/:id — Remove uma mesa (apenas administradores).

POST /reservas — Cria uma nova reserva, validando disponibilidade e a capacidade da mesa.

GET /reservas — Lista todas as reservas do usuário autenticado.

PATCH /reservas/:id/cancelar — Cancela uma reserva ativa.
```

## Authentication
The API uses Spring Security for authentication control. The following roles are available:

```
USER -> Standard user role for logged-in users.
ADMIN -> Admin role for managing tables (creating or deleting tables).
```
To access protected endpoints as an ADMIN user, provide the appropriate authentication credentials in the request header.

