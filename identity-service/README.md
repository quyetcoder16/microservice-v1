# Identity Service

![Java](https://img.shields.io/badge/Java-Spring%20Boot-green)
![Database](https://img.shields.io/badge/Database-MySQL-blue)
![Cache](https://img.shields.io/badge/Cache-Redis-red)
![Author](https://img.shields.io/badge/Author-Duong%20Xuan%20Quyet-orange)

**Identity Service** is a centralized backend system responsible for identity management, authentication, authorization, and user data storage. The system is designed for high performance using Redis Caching and implements multi-layer security mechanisms.

> **Author:** Duong Xuan Quyet
> **Version:** 1.0
> **Last Updated:** 11/28/2025

---

## 📑 Table of Contents
1. [Key Features](#-key-features)
2. [Tech Stack](#-tech-stack)
3. [Data Structure](#-data-structure)
4. [Setup & Deployment](#-setup--deployment)
5. [Project Structure](#-project-structure)

---

## 🚀 Key Features

### 🔐 Authentication & Authorization
* **Login/Register:** Supports Email/Password and **Google OAuth2**.
* **RBAC (Role-Based Access Control):** Granular permission management (User -> Role -> Permission).
* **JWT Security:** Uses Access Tokens for securing API requests.
* **Token Blacklist:** Handles immediate Logout/Revocation using Redis.

### 🛡️ Security
* **Brute Force Protection:** Automatically locks the account temporarily (15 minutes) after 5 failed login attempts.
* **Audit Logging:** Records history of Admin actions (Lock/Unlock users).
* **Secure Password:** BCrypt hashing.

### ⚡ Performance
* **Redis Caching:** Caches OTPs, temporary lock flags, and active user status to reduce database load.

---

## 🛠 Tech Stack

* **Core:** Java 17+, Spring Boot 3.x
* **Database:** MySQL 8.0
* **Cache:** Redis
* **Security:** Spring Security, OAuth2 Client, JWT
* **Build Tool:** Maven

---

## 💾 Data Structure

### 1. Database Schema (MySQL)
* **users:** Central table, stores login credentials and status.
* **social_accounts:** Links to external OAuth providers.
* **roles / permissions:** Manages RBAC.
* **audit_logs:** Stores Admin operation history.

### 2. Redis Key Schema
| Key Pattern | TTL | Description |
| :--- | :--- | :--- |
| `auth:otp:{email}` | 5 min | OTP code for verification. |
| `auth:login_fail:{email}` | 15 min | Counter for failed login attempts. |
| `auth:lock_temp:{user_id}` | 15 min | Temporary lock flag (if failed > 5 times). |
| `auth:blacklist:{token_id}` | Token Expiry | List of revoked tokens (Logout). |
| `user:status:{user_id}` | No limit | User status cache (Active/Banned). |

---

## ⚙️ Setup & Deployment

### Prerequisites
* JDK 17 or higher
* Maven
* Docker (Optional - for running MySQL/Redis)

### Configuration (`application.yml`)
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/identity_service
    username: root
    password: <your_password>
  data:
    redis:
      host: localhost
      port: 6379
jwt:
  signerKey: <your_secret_key>

```
### Running the Application
```aiignore
mvn clean spring-boot:run
```

## 📂 Project Structure
```aiignore
identity-service
├── src/main/java/com/quyet/identity
│   ├── common          # Constants, Base configurations
│   ├── configuration   # Security, Redis Config
│   ├── controller      # REST Controllers
│   ├── dto             # Request/Response Objects
│   ├── entity          # JPA Entities
│   ├── exception       # Global Exception Handler
│   ├── filter          # JWT & Logging Filters
│   ├── repository      # Data Access Layer
│   ├── service         # Business Logic
│   └── utils           # Helper classes
└── src/main/resources
    ├── db/migration    # Flyway Scripts
    └── application.yml
```