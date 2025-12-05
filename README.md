# 🔐 SEHK251 SSO - Single Sign-On System

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📖 Giới thiệu

Hệ thống **SEHK251 SSO** là một giải pháp Single Sign-On (Đăng nhập một lần) được phát triển như một phần của bài tập lớn môn **Công nghệ phần mềm** tại **Đại học Bách Khoa TP.HCM**. 

Hệ thống cho phép người dùng đăng nhập một lần và truy cập vào nhiều ứng dụng khác nhau mà không cần đăng nhập lại, sử dụng công nghệ JWT (JSON Web Token) để xác thực và phân quyền.

### 🎯 Mục tiêu dự án

- Xây dựng hệ thống xác thực tập trung cho nhiều ứng dụng
- Quản lý phiên đăng nhập và token một cách an toàn
- Phân quyền người dùng dựa trên vai trò (Role-based Access Control)
- Cung cấp API RESTful cho các ứng dụng client

## ✨ Tính năng chính

- ✅ **Đăng nhập**: Hỗ trợ đăng nhập bảo mật
- ✅ **JWT Authentication**: Sử dụng JWT để xác thực và duy trì phiên đăng nhập
- ✅ **Refresh Token**: Tự động làm mới token khi hết hạn
- ✅ **Role-based Authorization**: Phân quyền dựa trên vai trò người dùng
- ✅ **Token Invalidation**: Quản lý và vô hiệu hóa token khi đăng xuất
- ✅ **User Management**: Quản lý thông tin người dùng
- ✅ **Security**: Bảo mật với Spring Security và OAuth2 Resource Server

## 🏗️ Kiến trúc hệ thống

```
┌─────────────────┐
│   Client Apps   │
│  (Web/Mobile)   │
└────────┬────────┘
         │
         │ HTTP/REST API
         │
┌────────▼────────────────────────────────────┐
│         SSO Authentication Server           │
│  ┌──────────────────────────────────────┐  │
│  │      Controllers Layer               │  │
│  │  - AuthenticationController          │  │
│  │  - UserController                    │  │
│  │  - LoginController                   │  │
│  └──────────────┬───────────────────────┘  │
│                 │                           │
│  ┌──────────────▼───────────────────────┐  │
│  │      Service Layer                   │  │
│  │  - AuthenticationService             │  │
│  │  - UserService                       │  │
│  │  - JwtService                        │  │
│  └──────────────┬───────────────────────┘  │
│                 │                           │
│  ┌──────────────▼───────────────────────┐  │
│  │      Repository Layer                │  │
│  │  - UserRepository                    │  │
│  │  - RoleRepository                    │  │
│  │  - InvalidatedTokenRepository        │  │
│  └──────────────┬───────────────────────┘  │
└─────────────────┼───────────────────────────┘
                  │
         ┌────────▼────────┐
         │   PostgreSQL    │
         │    Database     │
         └─────────────────┘
```

## 🛠️ Công nghệ sử dụng

### Backend Framework
- **Spring Boot 3.5.5** - Framework chính
- **Spring Security** - Bảo mật và xác thực
- **Spring Data JPA** - ORM và truy vấn database
- **Spring OAuth2 Resource Server** - Xử lý JWT

### Database
- **PostgreSQL** - Cơ sở dữ liệu quan hệ
- **Hibernate** - ORM implementation

### Libraries & Tools
- **Lombok 1.18.34** - Giảm boilerplate code
- **MapStruct 1.6.2** - Object mapping
- **Thymeleaf** - Template engine cho UI
- **Spring Validation** - Validation dữ liệu
- **Spring Dotenv** - Quản lý biến môi trường

### Build Tool
- **Maven** - Dependency management và build automation

## 📋 Yêu cầu hệ thống

- **Java**: JDK 21 trở lên
- **Maven**: 3.6+ 
- **PostgreSQL**: 12+ 
- **IDE**: IntelliJ IDEA, Eclipse, hoặc VS Code (khuyến nghị IntelliJ IDEA)

## 🚀 Hướng dẫn cài đặt

### 1. Clone repository

```bash
git clone https://github.com/your-username/SEHK251_SSO.git
cd SEHK251_SSO
```

### 2. Cấu hình Database

Tạo database PostgreSQL:

```sql
CREATE DATABASE sso_database;
CREATE SCHEMA sso_schema;
```

### 3. Cấu hình biến môi trường

Tạo file `.env` trong thư mục gốc của project với nội dung:

```env
# Server Configuration
SERVER_PORT=8080
SERVER_CONTEXT_PATH=/api

# Database Configuration
SPRING_DATASOURCE_DRIVER=org.postgresql.Driver
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/sso_database
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password
SPRING_DATASOURCE_MAX_POOL_SIZE=10
SPRING_DATASOURCE_MIN_IDLE=5
SPRING_DATASOURCE_IDLE_TIMEOUT=300000

# JPA Configuration
SPRING_JPA_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=true
SPRING_JPA_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
SPRING_JPA_TIMEZONE=UTC
SPRING_JPA_DEFAULT_SCHEMA=sso_schema

# JWT Configuration
JWT_SIGNER_KEY=your_secret_key_here_minimum_32_characters_long
JWT_VALID_DURATION=3600
JWT_REFRESHABLE_DURATION=86400

# CORS Configuration
CONFIG_ALLOWED_ORIGIN=http://localhost:3000

# Application Configuration
APP_SEED_ADMIN=true
```

> ⚠️ **Lưu ý**: Thay đổi các giá trị `your_username`, `your_password`, và `JWT_SIGNER_KEY` theo môi trường của bạn.

### 4. Build và chạy ứng dụng

#### Sử dụng Maven Wrapper (khuyến nghị)

```bash
# Build project
./mvnw clean install

# Chạy ứng dụng
./mvnw spring-boot:run
```

#### Sử dụng Maven đã cài đặt

```bash
# Build project
mvn clean install

# Chạy ứng dụng
mvn spring-boot:run
```

#### Chạy file JAR

```bash
# Build JAR file
./mvnw clean package

# Chạy JAR
java -jar target/SSO_demo-0.0.1-SNAPSHOT.jar
```

### 5. Kiểm tra ứng dụng

Mở trình duyệt và truy cập:
- **API Base URL**: `http://localhost:8080/api`
- **Login Page**: `http://localhost:8080/api/login`

## 📚 API Documentation

### Authentication Endpoints

#### 1. Đăng ký tài khoản mới
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "user@example.com",
  "password": "SecurePassword123",
  "firstName": "Nguyen",
  "lastName": "Van A"
}
```

#### 2. Đăng nhập
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "user@example.com",
  "password": "SecurePassword123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 3600
}
```

#### 3. Làm mới token
```http
POST /api/auth/refresh
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### 4. Đăng xuất
```http
POST /api/auth/logout
Authorization: Bearer {token}
```

### Security Features
- ✅ Password encryption với BCrypt
- ✅ JWT signature verification
- ✅ Token expiration và refresh mechanism
- ✅ CORS configuration
- ✅ SQL Injection prevention với JPA
- ✅ XSS protection với Spring Security

## 📁 Cấu trúc thư mục

```
SEHK251_SSO/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/sso_demo/
│   │   │       ├── config/          # Cấu hình Spring Security, CORS, etc.
│   │   │       ├── controller/      # REST Controllers
│   │   │       ├── dto/             # Data Transfer Objects
│   │   │       ├── entity/          # JPA Entities
│   │   │       ├── exception/       # Custom Exceptions
│   │   │       ├── mapper/          # MapStruct Mappers
│   │   │       ├── repository/      # JPA Repositories
│   │   │       ├── service/         # Business Logic
│   │   │       ├── utils/           # Utility Classes
│   │   │       └── SsoDemoApplication.java
│   │   └── resources/
│   │       ├── application.yaml     # Application configuration
│   │       ├── static/              # Static resources
│   │       └── templates/           # Thymeleaf templates
│   └── test/                        # Test files
├── .env                             # Environment variables (gitignored)
├── .gitignore
├── pom.xml                          # Maven configuration
└── README.md
```

## 🤝 Đóng góp

Dự án này là bài tập lớn môn học, nhưng mọi đóng góp và góp ý đều được hoan nghênh!

### Quy trình đóng góp:
1. Fork repository
2. Tạo branch mới (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Tạo Pull Request

## 📞 Liên hệ

Nếu có bất kỳ câu hỏi hoặc vấn đề nào, vui lòng liên hệ:

- **Email**: [your-email@hcmut.edu.vn]
- **GitHub Issues**: [Create an issue](https://github.com/your-username/SEHK251_SSO/issues)

## 📄 License

Dự án này được phát triển cho mục đích học tập tại Đại học Bách Khoa TP.HCM.

---

## 🎓 Tài liệu tham khảo

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [JWT.io](https://jwt.io/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [MapStruct Documentation](https://mapstruct.org/)

## 🐛 Known Issues

- [ ] Chưa implement rate limiting cho API
- [ ] Chưa có email verification khi đăng ký
- [ ] Chưa có forgot password functionality

## 🚧 Roadmap

- [ ] Thêm email verification
- [ ] Implement forgot password
- [ ] Thêm 2FA (Two-Factor Authentication)
- [ ] API rate limiting
- [ ] Logging và monitoring
- [ ] Docker containerization
- [ ] CI/CD pipeline

---

<div align="center">
  <p>Made with ❤️ by HCMUT Students</p>
  <p>© 2024-2025 SEHK251 SSO Project</p>
</div>
