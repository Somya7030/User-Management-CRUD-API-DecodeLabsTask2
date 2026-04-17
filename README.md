# 🚀 User Management CRUD API

A complete REST API built using Spring Boot for managing users with full CRUD operations and database integration.

---

## 🛠 Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- H2 In-Memory Database
- Maven

---

## 📂 Project Structure

in.scalive
├── controller
├── service
├── repository
├── model
├── exception
└── config

---

## ✨ Features

- Create User (POST /users)
- Get All Users (GET /users)
- Update User (PUT /users/{id})
- Delete User (DELETE /users/{id})
- Input Validation using @Valid
- Global Exception Handling
- Duplicate Email Check
- Database Integration (H2)

---

## 🔗 API Endpoints

### 🔹 Get All Users
GET /users

### 🔹 Create User
POST /users

#### Sample Request:
```json
{
  "firstName": "Somya",
  "lastName": "Saxena",
  "email": "somya@gmail.com",
  "phone": "9876543210"
}
🔹 Update User

PUT /users/{id}

Sample Request:
{
  "firstName": "Updated",
  "lastName": "User",
  "email": "updated@gmail.com",
  "phone": "9876543210"
}
🔹 Delete User

DELETE /users/{id}

⚠️ Error Handling
400 → Validation errors
409 → Duplicate email
500 → Internal server error
🧪 Run Locally
Clone the repository:
git clone https://github.com/YOUR_USERNAME/user-management-crud-api.git
Navigate to project folder:
cd user-management-crud-api
Run the application:
mvn spring-boot:run
🌐 Base URL

http://localhost:8080/users

🧠 Learnings
REST API design
CRUD operations
Database integration using JPA
Validation and exception handling
Backend debugging
📌 Author

Somya Saxena
