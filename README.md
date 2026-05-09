# Student Management Backend

A Spring Boot REST API for managing student records with JWT-based authentication and role-aware access control foundations. This project demonstrates secure user registration and login, stateless request processing, and CRUD operations backed by MySQL and Spring Data JPA.

## Highlights

- JWT-based authentication for protected API access
- BCrypt password hashing for secure credential storage
- Student management REST endpoints for create, read, and delete operations
- Batch student creation endpoint for efficient data entry
- MySQL persistence with Spring Data JPA and Hibernate
- Input validation using Jakarta Validation
- Developer-friendly setup with Maven Wrapper and Spring Boot DevTools

## Technology Stack

- Java 25
- Spring Boot 4.0.6
- Spring Security
- Spring Data JPA
- Spring Web MVC
- Jakarta Validation
- MySQL
- JSON Web Token (`jjwt` 0.11.5)
- Lombok
- Maven

## Project Structure

- `src/main/java/dev/raseen/studentmanagement/controller`:
  REST controllers for authentication and student APIs
- `src/main/java/dev/raseen/studentmanagement/service`:
  business logic for authentication, user loading, and student management
- `src/main/java/dev/raseen/studentmanagement/security`:
  JWT utility and request filter logic
- `src/main/java/dev/raseen/studentmanagement/repository`:
  JPA repositories for users and students
- `src/main/resources/application.properties`:
  database and JWT configuration

## Authentication Design

The backend includes a custom authentication flow built with Spring Security:

- `POST /auth/register` creates a new user account
- passwords are stored using BCrypt hashing
- `POST /auth/login` authenticates a username and password
- successful login returns a JWT token
- protected routes require a valid `Authorization: Bearer <token>` header

Core authentication components:

- `SecurityConfig`:
  defines the security filter chain and public/protected routes
- `AuthService`:
  handles registration and login operations
- `CustomUserDetailsService`:
  loads user credentials from the database for Spring Security
- `JwtUtil`:
  generates and validates JWT tokens
- `JwtFilter`:
  inspects incoming bearer tokens before protected endpoints are processed

## Security Notes

This project is resume-ready as a learning-focused backend that demonstrates practical authentication concepts. The current implementation includes JWT and BCrypt integration, and the codebase also highlights important security engineering considerations such as:

- ensuring invalid or expired bearer tokens are handled gracefully in the filter chain
- returning clear authentication failure responses for bad login attempts
- configuring stateless security behavior explicitly for JWT-protected APIs

These are the kinds of production-hardening concerns typically addressed as part of ongoing backend refinement.

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Authenticate user and return JWT |

### Student Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/students` | Retrieve all students |
| GET | `/api/students/{id}` | Retrieve a student by ID |
| POST | `/api/students` | Create a new student |
| POST | `/api/students/batch` | Create multiple students |
| DELETE | `/api/students/{id}` | Delete a student by ID |

## Sample Requests

### Register User

```http
POST /auth/register
Content-Type: application/json
```

```json
{
  "username": "Alice",
  "password": "password123",
  "role": "USER"
}
```

### Login User

```http
POST /auth/login
Content-Type: application/json
```

```json
{
  "username": "Alice",
  "password": "password123"
}
```

### Create Students in Batch

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

## Getting Started

### Prerequisites

- Java 25 or higher
- MySQL Server
- Maven installed, or use the included Maven Wrapper

### Configuration

Update [application.properties](/src/main/resources/application.properties) with your local database settings:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_DB_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your-secret-key-with-at-least-32-characters
```

For a clean setup after cloning, copy [application.properties.example](src/main/resources/application.properties.example) to `src/main/resources/application.properties` and fill in your local values. The real `application.properties` is ignored by Git so personal credentials and secrets are not committed.

### Create the Database

```sql
CREATE DATABASE your_DB_name;
```

### Run the Application

With Maven Wrapper:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The API will be available at:

`http://localhost:8080`

## Dependencies Referenced from pom.xml

This project currently uses the following major dependencies from [pom.xml](/C:/Users/acer/Documents/SpringBoot-Lerning/student-management-backend/pom.xml):

- `spring-boot-starter-webmvc`
- `spring-boot-starter-security`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-validation`
- `spring-boot-starter-actuator`
- `mysql-connector-j`
- `jjwt-api`
- `jjwt-impl`
- `jjwt-jackson`
- `lombok`

## Resume Value

This project demonstrates hands-on backend engineering skills in:

- designing RESTful APIs with Spring Boot
- implementing JWT authentication and password hashing
- integrating relational persistence with JPA and MySQL
- structuring a layered backend architecture with controller, service, repository, and security packages
- applying validation and secure request handling practices in a Java web application
