🛒 Spring Boot E-Commerce Backend

📌 Overview

This is a backend e-commerce system built using Spring Boot and MySQL.
It provides RESTful APIs for managing users, products, and orders with secure authentication.

---

🚀 Features

- 🔐 Authentication & Authorization (JWT)
- 👤 User Management
- 🛍️ Product Management (CRUD)
- 📦 Order Management
- 🔑 Role & Permission System
- 🛡️ Spring Security Integration

---

🛠️ Tech Stack

- Java
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- MySQL
- Migration (Flyway)
- Maven

---

📂 Project Structure

- Controller → Handle API requests
- Service → Business logic
- Repository → Database access
- Entity → Database models

---

⚙️ How to Run

1. Clone the project:

git clone https://github.com/vichekafc07-spec/spring-boot-e-commerce.git

2. Configure database in "application.yaml"

3. Run the project:

mvn spring-boot:run

4. API runs at:

http://localhost:8080

---

🔗 API Example

Login

POST /api/v1/auth/login

Get Products

GET /api/v1/products

---

📸 Future Improvements

- Payment integration
- Frontend (React / Angular)
- Docker deployment

---

👨‍💻 Author

Vicheka Sorl

- GitHub: https://github.com/vichekafc07-spec

---

⭐ If you like this project, give it a star!
