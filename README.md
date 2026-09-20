# Payment Gateway API

A simplified payment gateway REST API (inspired by PicPay/Asaas), built as a learning project to practice Java, Spring Boot, JPA, and REST API design from the ground up — starting with plain Java OOP before introducing any framework.

## Domain

The system models two kinds of users and money transfers between them:

- **User** — has a CPF, name, email, balance, and a type (`COMMON` or `MERCHANT`).
- **Transfer** — moves an amount from a payer to a payee, and tracks its outcome via a status (`PENDING`, `COMPLETED`, `FAILED`).

### Business rules

- A `MERCHANT` user can receive money but cannot initiate a transfer (cannot be a payer).
- A user cannot transfer money to themselves.
- A transfer cannot be executed if the payer does not have sufficient balance.
- Every transfer attempt is persisted, whether it succeeds or fails.

## Architecture

The project follows a layered structure:

```
domain/          Core entities (User, Transfer) — validate their own invariants
services/        Business logic orchestration (TransferService)
repositories/     Spring Data JPA interfaces for persistence
controllers/     REST endpoints
dto/             Request/response objects, decoupled from JPA entities
exceptions/      Domain exception hierarchy + global HTTP error handling
```

A few deliberate design decisions worth noting:

- **Entities validate structural invariants in their constructors** (e.g. a `User` cannot be created with a blank CPF or a negative balance). Business rules that depend on the interaction between multiple entities (e.g. "a merchant cannot pay") live in the service layer instead, since they only make sense at execution time — not when an entity is simply being reconstructed from the database.
- **Dependency injection is constructor-based throughout**, following Spring's own convention.
- **A custom `DomainException` hierarchy** separates expected business errors (insufficient balance, invalid transfer, etc.) from unexpected runtime bugs, so a global exception handler can safely translate business errors into proper HTTP responses (`400`, `404`) without masking real application bugs as client errors.
- **DTOs decouple the API contract from the JPA entities** — the API never exposes `User`/`Transfer` directly, so internal persistence details can change without breaking API consumers.

## Tech stack

- Java 21
- Spring Boot 4.1.1 (Web, Data JPA, Validation)
- PostgreSQL 16
- Docker & Docker Compose
- JUnit 5 + Mockito
- Maven

## Running the project

Requires Docker and Docker Compose installed.

```bash
git clone https://github.com/kennysboring/payment_gateway.git
cd payment_gateway
cp .env.example .env   # fill in your own local credentials
docker compose up --build
```

The API will be available at `http://localhost:8080`.

## Running the tests

```bash
mvn test
```

Test coverage includes happy-path and failure scenarios for both entities and the service layer (business rule violations, boundary values, and repository interaction verified via Mockito).

## API endpoints

### Users

| Method | Endpoint         | Description                  |
|--------|------------------|-------------------------------|
| POST   | `/users`         | Create a new user             |
| GET    | `/users`         | List all users                |
| GET    | `/users/{id}`    | Get a user by id              |

**POST /users**
```json
{
  "cpf": "000.000.000-01",
  "name": "Jane Doe",
  "email": "jane@email.com",
  "balance": 100.00,
  "userType": "COMMON"
}
```

### Transfers

| Method | Endpoint       | Description                    |
|--------|----------------|----------------------------------|
| POST   | `/transfers`   | Execute a transfer between users |
| GET    | `/transfers`   | List all transfers               |

**POST /transfers**
```json
{
  "payerId": "uuid-of-payer",
  "payeeId": "uuid-of-payee",
  "amount": 50.00
}
```

### Error responses

Validation and business rule violations return a structured error body:

```json
{
  "timestamp": "2026-09-20T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "amount: must not be null"
}
```

## Known limitations

This project is a learning exercise and intentionally leaves a few things simplified:

- **`debit`/`credit` are not wrapped in a database transaction.** Today this is safe because the transferred amount is validated once and reused for both operations, but it is not a true atomic guarantee — a future business rule added to `credit()` could reintroduce a partial-failure window. This is a known trade-off, not an oversight.
- **No authentication/authorization** — every endpoint is open.
- **No CPF/email uniqueness enforcement** yet.
- **No pagination** on list endpoints.

## Project history

This project was built incrementally, phase by phase: plain Java OOP and domain modeling first, then Maven, then Spring Boot's core (dependency injection, REST controllers), then persistence with PostgreSQL/JPA, and finally centralized error handling and input validation.
