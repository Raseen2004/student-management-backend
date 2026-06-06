# Student Management Backend

A secure Spring Boot REST API for student record management with JWT-based authentication, MySQL persistence, Redis-backed caching, and layered backend architecture. This project was built to practice real-world backend concepts including stateless authentication, request validation, caching, centralized exception handling, and clean separation of controller, service, repository, and security responsibilities.

## Overview

The application provides:

- User registration and login with JWT token generation
- Protected student management APIs
- Redis caching for student retrieval
- Paginated student retrieval
- Single and batch student creation
- Student update support
- Student deletion by ID
- Custom error handling for application and security responses

This project is designed as a learning-focused backend, while still following patterns that are commonly expected in production-style Spring applications.

## Tech Stack

- Java 25
- Spring Boot 4.0.6
- Spring Web
- Spring Security
- Spring Data JPA
- Spring Data Redis
- Jakarta Validation
- MySQL
- Redis
- JWT (`jjwt` 0.11.5)
- Lombok
- Maven

## Architecture

The codebase follows a layered structure:

- `controller`
  Exposes REST endpoints and handles HTTP requests/responses.
- `service`
  Contains business logic for authentication and student operations.
- `repository`
  Provides data access using Spring Data JPA.
- `entity`
  Defines JPA-mapped domain models.
- `security`
  Contains JWT generation, token validation, request filtering, and security handlers.
- `exception`
  Centralizes error response handling for application-level exceptions.

## Key Features

### Authentication and Security

- User registration with duplicate-username protection
- Login with Spring Security `AuthenticationManager`
- BCrypt password hashing before persistence
- JWT generation after successful login
- Request filtering using a custom `JwtFilter`
- Public authentication endpoints and protected student endpoints
- Custom JSON responses for unauthorized and forbidden requests

### Student Management

- Retrieve students using Redis-backed caching with pagination support
- Retrieve students with pagination support
- Fetch a student by ID
- Create a single student record
- Create multiple students in one request
- Partially update an existing student
- Delete a student by ID

### Caching

- Redis cache integration using Spring Cache abstraction
- Cached student list retrieval to reduce repeated database reads
- Cached student-by-ID lookup for frequently accessed records
- Time-to-live configuration for cache entries

### Error Handling

- Global exception handling using `@RestControllerAdvice`
- Custom application error response model
- Security-layer handling for `401 Unauthorized` and `403 Forbidden`

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/auth/register` | Register a new user |
| `POST` | `/auth/login` | Authenticate user and return a JWT token |

### Students

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/students` | Retrieve all students with pagination |
| `GET` | `/api/students/{id}` | Retrieve a student by ID |
| `POST` | `/api/students` | Create a new student |
| `POST` | `/api/students/batch` | Create multiple students |
| `PATCH` | `/api/students/{id}` | Partially update a student |
| `DELETE` | `/api/students/{id}` | Delete a student by ID |

## Sample Requests

### Register

```http
POST /auth/register
Content-Type: application/json
```

```json
{
  "username": "alice",
  "password": "password123",
  "role": "USER"
}
```

### Login

```http
POST /auth/login
Content-Type: application/json
```

```json
{
  "username": "alice",
  "password": "password123"
}
```

### Create Student

```http
POST /api/students
Content-Type: application/json
Authorization: Bearer <jwt-token>
```

```json
{
  "name": "Aarav Sharma",
  "email": "aarav.sharma@bishop.ac.in",
  "course": "Computer Science"
}
```

### Batch Create Students

```http
POST /api/students/batch
Content-Type: application/json
Authorization: Bearer <jwt-token>
```

```json
[
  {
    "name": "Aarav Sharma",
    "email": "aarav.sharma@bishop.ac.in",
    "course": "Mechanical Engineering"
  },
  {
    "name": "Diya Patel",
    "email": "diya.patel@bishop.ac.in",
    "course": "Computer Science"
  }
]
```

### Update Student

```http
PATCH /api/students/1
Content-Type: application/json
Authorization: Bearer <jwt-token>
```

```json
{
  "name": "Aarav Sharma",
  "email": "aarav.sharma.updated@bishop.ac.in"
}
```

## Configuration

Use [application.properties.example](C:/Users/acer/Documents/SpringBoot-Lerning/student-management-backend/src/main/resources/application.properties.example) as the base configuration and create your local `src/main/resources/application.properties`.

Example:

```properties
spring.application.name=studentManagement

spring.datasource.url=jdbc:mysql://localhost:3306/learning_springboot
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your-secret-key-with-at-least-32-characters

spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.cache.type=redis
```

## Getting Started

### Prerequisites

- Java 25
- MySQL Server
- Redis Server
- Maven, or use the included Maven Wrapper

### Database Setup

Create a database before running the application:

```sql
CREATE DATABASE learning_springboot;
```

### Run the Application

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

Application base URL:

`http://localhost:8080`

Redis default URL:

`localhost:6379`

## Example Error Response

Application and security errors are returned as structured JSON responses.

```json
{
  "message": "Student not found with id: 9999",
  "status": 404,
  "timestamp": "2026-06-05T10:30:00"
}
```

## What This Project Demonstrates

This project highlights practical backend development skills relevant to entry-level and early-career Java backend roles:

- Building REST APIs with Spring Boot
- Implementing JWT-based authentication and stateless request security
- Working with relational databases using JPA and Hibernate
- Integrating Redis caching to improve repeated read performance
- Structuring a backend with clear separation of concerns
- Applying validation and exception handling for cleaner API behavior
- Securing user credentials with BCrypt password hashing

## Future Improvements

Planned or natural next steps for extending this project include:

- Role-based authorization rules for admin-only operations
- Swagger/OpenAPI documentation
- Unit and integration tests for controllers and services
- Docker Compose setup for MySQL and Redis
- Docker-based local development setup

## Author

Built as a hands-on Spring Boot backend project for strengthening Java, Spring Security, REST API, and database integration skills.
