# 🛒 E-commerce Backend API

An **end-to-end e-commerce backend API** built using **Java and Spring Boot**, designed with a strong focus on clean separation of concerns, security, and real-world backend design practices.  
The system implements the complete **core e-commerce workflow** (excluding payments and logistics by design).

---

## 🚀 Features

### 🔐 Authentication & Authorization
- JWT-based authentication
- Role-based access control (User / Admin)
- Secure login and registration using Spring Security

### 👤 User & Admin Management
- User registration and authentication
- Admin-controlled user management
- Role-based endpoint protection

### 🛍️ Product Catalog
- Product and category management
- Filtering and search support
- Admin-controlled product creation, update, and deletion

### 🛒 Cart & Wishlist
- User-specific cart management
- Wishlist add/remove functionality

### 📦 Order Management
- Order creation from cart
- Order lifecycle handling
- User order history
- Admin access to all orders and reports

### 📄 API Documentation
- Interactive API documentation using Swagger (OpenAPI)

---

## 🧱 Tech Stack

### Backend
- Java
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)

### Database
- PostgreSQL
- Flyway (Database migrations)

### Security
- JWT (JSON Web Tokens)
- Role-based authorization

### Documentation
- Swagger / OpenAPI

---

## 🏗️ Architecture Overview

The application follows a **feature-based modular architecture** with **layered separation of concerns**.

Each feature (such as Auth, Cart, Order, Admin) is implemented as a **vertical slice**, containing its own controllers, orchestrators, services, and repositories.

### Layer Responsibilities

#### Controller
- Handles HTTP requests and responses
- Performs request validation
- Delegates execution to orchestrators
- Contains no business logic

#### Orchestrator
- Coordinates application workflows
- Handles request-to-domain mapping (DTO → Entity)
- Composes multiple services to fulfill a use case
- Manages flow control across domains
- Keeps controllers thin and services reusable

#### Service
- Encapsulates **core business logic**
- Enforces domain rules and validations
- Handles state transitions
- Interacts with repositories
- Remains orchestration-agnostic and reusable

#### Repository
- Abstracts persistence logic
- Uses Spring Data JPA for database access

### Key Architectural Characteristics
- Feature-based modularization (vertical slicing)
- Layered architecture within each feature  
  **Controller → Orchestrator → Service → Repository**
- Clear separation between orchestration and business logic
- DTO pattern for request/response isolation
- Repository pattern for persistence abstraction
- JWT filter-based security using Spring Security
- Configuration-as-code for security, migrations, and API documentation

---

## 🗂️ Project Structure (High Level)

```text
src/main/java
 ├── auth
 ├── admin
 ├── cart
 │   ├── controller
 │   ├── orchestrator
 │   ├── service
 │   ├── repository
 │   └── dto / entity
 ├── order
 ├── product
 ├── security
 └── config
```

---

## 🧪 Testing

The project includes multiple levels of automated testing to ensure correctness, reliability, and maintainability of the system.

Unit Testing

Unit tests verify individual components in isolation.

Frameworks: JUnit 5, Mockito

Service layer business logic is tested independently

External dependencies are mocked

Example scope:

Business rule validation

Order lifecycle logic

Cart operations

Product management rules

Controller Testing

Controller slice tests verify the HTTP layer.

Implemented using @WebMvcTest

Tests request validation

Verifies response status and payloads

Security filters and endpoint protection are validated

These tests ensure that the API contract behaves correctly.

Repository Testing

Repository tests validate database persistence logic.

Implemented using @DataJpaTest

Runs against a real PostgreSQL container

Uses Testcontainers for environment isolation

These tests verify:

Entity mapping

Query correctness

Database constraints

Repository behavior

Integration Testing

Integration tests verify that multiple layers of the application work together correctly.

These tests start the Spring Boot application context and validate interactions between:

Controllers

Orchestrators

Services

Repositories

Database

Tools used:

@SpringBootTest

Testcontainers for PostgreSQL

Example scenarios tested:

User registration flow

Product creation by admin

Cart to order conversion

Inventory validation during order creation

Integration tests ensure that real application workflows behave correctly across layers.

End-to-End (E2E) Testing

End-to-end tests simulate complete user workflows through the API, closely resembling real-world usage.

These tests validate the entire system behavior from HTTP request → database persistence → final response.

Example flows:

User registration → login → JWT generation

Product browsing → cart addition

Checkout process → order creation

Viewing order history

E2E tests ensure the system functions correctly as a complete backend application.

Test Infrastructure

The project uses Testcontainers to provide reproducible testing environments.

Advantages:

Real database testing

No dependency on local database setup

Consistent CI/CD test execution

Production-like testing environment

Testing Stack

JUnit 5

Mockito

Spring Boot Test

Testcontainers

PostgreSQL

---

## ⚙️ Getting Started

### Prerequisites
- Java 17+
- Maven
- PostgreSQL

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/Anzyll/ecommerce-backend-api.git
   ```
2. Configure PostgreSQL credentials in `application.yml`
3. Flyway migrations run automatically on application startup
4. Start the application:
   ```bash
   mvn spring-boot:run
   ```

---

## 📘 API Documentation

Once the application is running, access Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

---
