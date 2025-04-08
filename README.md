# 📝 Task Manager App

A full-stack Task Manager application built using **Spring Boot** for the backend and **Angular** for the frontend. It provides user authentication and complete task CRUD operations.

## 📁 Project Structure

Treinetic-Assignment-Final/
├── task-manager-backend/ # Spring Boot API
├── task-manager-frontend/ # Angular UI
└── README.md

---
## 🛠️ Tech Stack

### Frontend
- Angular 18+
- TypeScript
- Bootsrap (UI Components)
- RxJS & Angular Services

### Backend
- Java 17+
- Spring Boot
- Spring Security
- JWT (io.jsonwebtoken)
- Hibernate + JPA
- MySQL

---

## 🚀 Getting Started

### 🔧 Backend (Spring Boot)

#### Prerequisites

- Java 17 or higher
- Maven
- MySQL

#### Steps

1. Clone the repository:

   git clone https://github.com/wasuradasun1/Treinetic-Assignment-Final.git
   cd Treinetic-Assignment-Final/task-manager-backend

2. Create a MySQL database:

CREATE DATABASE task_manager;

3. Configure your application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/task_manager?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update

**Configure your aplication.yml file instead of application.properties**

server:
port: 8080
servlet:
context-path: /api

spring:
datasource:
url: jdbc:mysql://localhost:3306/task_manager?useSSL=false&serverTimezone=UTC
username: root
password: password
driver-class-name: com.mysql.cj.jdbc.Driver
jpa:
hibernate:
ddl-auto: update
show-sql: true
properties:
hibernate:
dialect: org.hibernate.dialect.MySQL8Dialect
format_sql: true

app:
jwt:
secret: your-256-bit-secret-key-here-must-be-32-chars
expiration: 86400000 # 24 hours in milliseconds

4. Run the backend:

mvn clean install
mvn spring-boot:run

📍 API will be running at http://localhost:8080

### 🌐 Frontend (Angular)

#### Prerequisites

Node.js (v16+ recommended)
Angular CLI (Angular 18v)

## Steps

1. Navigate to the frontend directory:

cd Treinetic-Assignment-Final/task-manager-frontend

2. Install dependencies:

npm install

3. Start the Angular dev server:

ng serve

📍 Frontend will be running at http://localhost:4200

---

## 🚀 Features

### ✅ User Features
- User registration and login
- JWT-based authentication and authorization
- Create, update, delete, and list tasks
- Mark tasks as complete/incomplete
- Responsive UI with Angular

### 🔐 Security
- JWT (JSON Web Token) authentication
- Secure password hashing
---

## 🧪 API Overview (Backend)

| Method | Endpoint                  | Description              | Auth Required  |
|--------|---------------------------|--------------------------|----------------|
| POST   | `/api/auth/register`      | Register a new user      | ❌            |
| POST   | `/api/auth/authenticate`  | Authenticate user (JWT)  | ❌            |
| GET    | `/api/tasks`              | Get all tasks            | ✅            |
| GET    | `/api/tasks/{id}`         | Get task by ID           | ✅            |
| POST   | `/api/tasks`              | Create a new task        | ✅            |
| PUT    | `/api/tasks/{id}`         | Update an existing task  | ✅            |
| DELETE | `/api/tasks/{id}`         | Delete a task            | ✅            |
