# porto-backend

Portfolio Backend API built with **Spring Boot 3**, featuring JWT authentication, OAuth2 (GitHub), PDF generation, and full OpenAPI documentation.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.4.4 |
| Security | Spring Security · JWT (JJWT 0.12) · OAuth2 (GitHub) |
| Database | PostgreSQL 16 |
| Migration | Flyway |
| ORM | Spring Data JPA · Hibernate |
| PDF | Flying Saucer · OpenPDF · Jsoup |
| Docs | SpringDoc OpenAPI (Swagger UI) |
| Utilities | Lombok · MapStruct |
| Concurrency | Java 21 Virtual Threads |

## Features

- **Auth**: Register/Login with JWT access + refresh token, GitHub OAuth2 login
- **Portfolio CRUD**: Personal info, work experience, education, skills, projects, certifications, awards, languages
- **Public API**: View any user's portfolio by username (no auth required)
- **PDF Export**: Generate portfolio as downloadable PDF via Thymeleaf + Flying Saucer
- **Swagger UI**: Full API documentation at `/swagger-ui.html`
- **Health Check**: Actuator endpoints at `/actuator/health`

## API Endpoints

| Group | Base Path |
|---|---|
| Auth | `/api/auth/**` |
| Portfolio (protected) | `/api/portfolio/**` |
| Profile | `/api/profile/**` |
| Public | `/api/public/**` |

## Prerequisites

- Java 21+
- Maven 3.9+
- Docker & Docker Compose (for PostgreSQL)
- GitHub OAuth App (for OAuth2 login)

## Environment Variables

| Variable | Description | Default (dev) |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | Active profile (`dev`/`prod`) | `dev` |
| `DB_USERNAME` | PostgreSQL username | `portfolio_user` |
| `DB_PASSWORD` | PostgreSQL password | `portfolio_pass` |
| `JWT_SECRET` | JWT signing secret (min 256-bit) | dev default |
| `GITHUB_CLIENT_ID` | GitHub OAuth App Client ID | — |
| `GITHUB_CLIENT_SECRET` | GitHub OAuth App Client Secret | — |
| `CORS_ALLOWED_ORIGINS` | Allowed CORS origins | `http://localhost:5173` |
| `FRONTEND_URL` | Frontend base URL | `http://localhost:5173` |

## Running Locally

### 1. Start PostgreSQL with Docker

```bash
docker-compose up -d
```

This starts:
- **PostgreSQL** on `localhost:5432` (db: `portfolio_db`)
- **pgAdmin** on `http://localhost:5050` (email: `admin@portfolio.dev`, password: `admin`)

### 2. Configure GitHub OAuth App

1. Go to GitHub → Settings → Developer settings → OAuth Apps → New OAuth App
2. Set **Homepage URL**: `http://localhost:8080`
3. Set **Authorization callback URL**: `http://localhost:8080/login/oauth2/code/github`
4. Copy the **Client ID** and **Client Secret**

### 3. Run the Application

```bash
# Export required env vars
export GITHUB_CLIENT_ID=your_client_id
export GITHUB_CLIENT_SECRET=your_client_secret

# Run with Maven
./mvnw spring-boot:run
```

Or with all env vars inline:

```bash
GITHUB_CLIENT_ID=xxx GITHUB_CLIENT_SECRET=yyy ./mvnw spring-boot:run
```

### 4. Access

| Service | URL |
|---|---|
| API Base | `http://localhost:8080` |
| Swagger UI | `http://localhost:8080/swagger-ui.html` |
| OpenAPI Docs | `http://localhost:8080/v3/api-docs` |
| pgAdmin | `http://localhost:5050` |
| Health | `http://localhost:8080/actuator/health` |

## Project Structure

```
src/main/java/com/portfolio/
├── config/          # Security, OpenAPI, Scheduling config
├── controller/      # REST controllers (Auth, Portfolio, Profile, Public)
├── dto/             # Request & Response DTOs
├── entity/          # JPA entities + enums
├── repository/      # Spring Data JPA repositories
├── security/        # JWT filter, OAuth2 handler, UserDetails
└── service/         # Business logic

src/main/resources/
├── application.yml          # Base config
├── application-dev.yml      # Dev profile
├── application-prod.yml     # Prod profile
├── db/migration/            # Flyway SQL migrations
└── templates/               # Thymeleaf PDF template
```

## Build for Production

```bash
./mvnw clean package -DskipTests
java -jar target/porto-backend-1.0.0-SNAPSHOT.jar
```
