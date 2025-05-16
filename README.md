# Student Management System

A Spring Boot REST API application for managing student data.  
This project demonstrates CRUD operations, batch inserts, and PostgreSQL integration.

## Features

- Create, read, update, and delete student records
- Batch insert multiple students in one request
- Built with Java 17, Spring Boot 3.4.5, and PostgreSQL
- Uses Spring Data JPA for database interactions
- Easy-to-use RESTful API endpoints

## Technologies Used

- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- PostgreSQL
- Maven
- IntelliJ HTTP Client (for API testing)

## Getting Started

### Prerequisites

- Java 17 or higher installed
- PostgreSQL installed and running
- Maven installed

### Setup

#### 1. Clone the repository

```bash
git clone https://github.com/your-username/student-management-system.git
cd student-management-system
```
### 2. Configure your database
- In src/main/resources/application.properties, set the following:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/LearningDB
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.jdbc.time_zone=UTC
```
### 3. Create the PostgreSQL database

    CREATE DATABASE LearningDB;
### 4. Run the application

    mvn spring-boot:run
```
The application will be available at:
http://localhost:8080
 ```

API Endpoints
The following endpoints are available:

| Method | Endpoint               | Description                   |
|--------|------------------------|-------------------------------|
| GET    | /api/students          | Retrieve all students         |
| GET    | /api/students/{id}     | Retrieve student by ID        |
| POST   | /api/students          | Create a new student          |
| POST   | /api/students/batch    | Create multiple students      |
| DELETE | /api/students/{id}     | Delete student by ID          |

## API Documentation

### Endpoints

|Method|Endpoint|Description|
|-------|-------|-----------|
|GET    |/api/students          |Retrieve all students|
|GET    |/api/students/{id}     |Retrieve student by ID|
|POST   |/api/students          |Create a new student|
|POST   |/api/students/batch    |Create multiple students|
|DELETE |/api/students/{id}     |Delete student by ID|

### Sample Request

#### Create Multiple Students (POST /api/students/batch)
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
### Testing
Use IntelliJ HTTP Client, Postman, or similar tools to test the API.

**Example IntelliJ HTTP Client Request**
```
POST http://localhost:8080/api/students/batch
Content-Type: application/json

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

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.
