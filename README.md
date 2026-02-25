🛒 E-commerce Backend API

An end-to-end e-commerce backend API built using Java and Spring Boot, designed with a focus on clean separation of concerns, security, and real-world backend design practices.
The system implements the complete core e-commerce workflow.

🚀 Features
🔐 Authentication & Authorization

JWT-based authentication

Role-based access control (User / Admin)

Secure login and registration using Spring Security

👤 User & Admin Management

User registration and authentication

Admin-controlled user management

Role-based endpoint protection

🛍️ Product Catalog

Product and category management

Filtering and search support

Admin-controlled product creation, update, and deletion

🛒 Cart & Wishlist

User-specific cart management

Wishlist add/remove functionality

📦 Order Management

Order creation from cart

Order lifecycle handling

User order history

Admin access to all orders and reports

📄 API Documentation

Interactive API documentation using Swagger (OpenAPI)

🧱 Tech Stack

Backend

Java

Spring Boot

Spring Security

Spring Data JPA (Hibernate)

Database

PostgreSQL

Flyway (Database migrations)

Security

JWT (JSON Web Tokens)

Role-based authorization

Documentation

Swagger / OpenAPI

🏗️ Architecture Overview

The application follows a feature-based modular architecture with layered separation of concerns.

Each feature (such as Cart, Order, Admin, Auth) is implemented as a vertical slice containing its own controllers, orchestrators, services, and repositories.

Key architectural characteristics:

Feature-based modularization (vertical slicing)

Layered architecture within each feature (Controller → Orchestrator → Service → Repository)

Service layer acting as an Orchestrator, coordinating multi-step business workflows

DTO pattern for request/response separation

Repository pattern for persistence abstraction

JWT filter-based security using Spring Security

Configuration-as-code for security, migrations, and API documentation

This structure improves maintainability, testability, and scalability while keeping complexity under control.

🗂️ Project Structure (High Level)
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
🧪 Testing

Controller slice tests using @WebMvcTest

Unit tests for service and orchestrator layers using Mockito

Repository tests using @DataJpaTest

Integration testing with Testcontainers (in progress)

⚙️ Getting Started
Prerequisites

Java 17+

Maven

PostgreSQL

Setup

Clone the repository:

git clone https://github.com/Anzyll/ecommerce-backend-api.git

Configure PostgreSQL credentials in application.yml

Flyway migrations run automatically on application startup

Start the application:

mvn spring-boot:run
📘 API Documentation

Once the application is running, access Swagger UI at:

http://localhost:8080/swagger-ui.html