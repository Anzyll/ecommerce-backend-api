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

The project includes multiple levels of testing to ensure reliability and correctness of the system.

Unit Tests
Implemented using JUnit 5 and Mockito
Tests individual components such as service layer business logic
External dependencies are mocked

Controller Tests
Implemented using @WebMvcTest
Validates API endpoints, request validation, and response structure

Repository Tests
Implemented using @DataJpaTest
Verifies database interactions and custom queries

Integration Tests
Implemented using @SpringBootTest
Tests interaction between multiple layers (controller, service, repository)

End-to-End (E2E) Tests
Simulates complete API workflows:
Cart to order checkout


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
