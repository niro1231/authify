Authify: Spring Boot Security & JWT API 🔐
Authify is a RESTful API built with Spring Boot that provides a complete user authentication and role-based authorization system using JSON Web Tokens (JWT). It serves as a robust backend foundation for modern web applications.

✨ Features
    JWT Authentication: Secure stateless authentication using JWTs.
    
    Role-Based Access Control: Differentiated access levels for USER and ADMIN roles.
    
    Secure Endpoints: Endpoints are protected based on user roles.
    
    User Management (Admin): Admins can add, view, and delete users.
    
    Profile Management: Authenticated users can view and update their own profiles.
    
    HttpOnly Cookie: JWTs are sent back in secure, HttpOnly cookies to prevent XSS attacks.
    
    Credential Hashing: Passwords are securely hashed using BCrypt.
    
    Validation: Input validation on request data.

🛠️ Technologies Used
    Java 17+
    
    Spring Boot 3+
    
    Spring Security 6+
    
    Spring Data JPA (Hibernate)
    
    MySQL Database
    
    JSON Web Token (JWT) for authentication
    
    Lombok for reducing boilerplate code
    
    Maven/Gradle for dependency management

🔐 API Endpoints - http://localhost:8080/api/v1.0
Public Endpoints
  POST	/register
    {
      "email": "testuser@example.com",
      "name": "Test User",
      "role": "USER",
      "password": "password123"
    }
  POST	/login
    {
      "email": "testuser@example.com",
      "password": "password123"
    }

Authenticated Endpoints (USER & ADMIN)
  GET	/profile
  PUT	/profile/updatename	
    {
      "name": "My Updated Name"
    }

Admin Only Endpoints
  GET	/admin/getallusers	
  POST	/admin/adduser
    {
      "email": "anotheruser@example.com",
      "name": "Another User",
      "role": "USER",
      "password": "password456"
    }
  DELETE	/admin/deleteuser/{userId}


