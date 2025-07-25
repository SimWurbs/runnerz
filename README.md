# Runnerz API

A Spring Boot REST API for tracking running activities with PostgreSQL database and Docker integration.

## 🏃‍♂️ About

Runnerz is a comprehensive running tracker API built with Spring Boot 3. It allows users to create, read, update, and delete running activities while integrating with external user data from JSONPlaceholder.

## ✨ Features

- 🏃 **CRUD Operations** for running activities
- 🐘 **PostgreSQL Database** with Docker Compose integration
- 🧪 **Comprehensive Testing** with Testcontainers
- 👥 **External User API** integration (JSONPlaceholder)
- 🔄 **Sample Data Loading** for development
- 📊 **Data Validation** and error handling

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Data JDBC**
- **PostgreSQL 15**
- **Docker & Docker Compose**
- **Maven**
- **Testcontainers** for integration testing

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Docker Desktop
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Simwurbs/runnerz.git
   cd runnerz/runnerz
   ```

2. **Start the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   The application will automatically:
   - Start PostgreSQL container via Docker Compose
   - Create the database schema
   - Load sample data (10 runs)
   - Start the REST API on `http://localhost:8080`

### Windows Users
```cmd
mvnw.cmd spring-boot:run
```

## 📚 API Endpoints

### Runs

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/runs` | Get all runs |
| `GET` | `/api/runs/{id}` | Get run by ID |
| `POST` | `/api/runs` | Create new run |
| `PUT` | `/api/runs/{id}` | Update existing run |
| `DELETE` | `/api/runs/{id}` | Delete run |

### Users (External API)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/users` | Get all users |
| `GET` | `/api/users/{id}` | Get user by ID |

## 🧪 Testing

Run all tests (includes integration tests with Testcontainers):

```bash
./mvnw test
```

The test suite includes:
- Unit tests for service layer
- Integration tests with real PostgreSQL (via Testcontainers)
- REST API endpoint testing

## 📦 Sample Data

The application includes 10 sample runs that are automatically loaded on first startup:

```json
{
  "title": "Morning Run",
  "startedOn": "2024-01-01T06:00:00",
  "completedOn": "2024-01-01T07:00:00", 
  "miles": 5,
  "location": "OUTDOOR"
}
```

## 🐳 Docker

### Manual Database Setup

If you prefer to manage PostgreSQL manually:

```bash
# Start PostgreSQL
docker compose up -d

# Stop PostgreSQL  
docker compose down
```

### Database Configuration

The application connects to PostgreSQL with these default settings:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/runnerz
spring.datasource.username=simwurbs
spring.datasource.password=password
```

## 🔧 Configuration

### Application Properties

Key configuration options in `src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/runnerz
spring.datasource.username=simwurbs
spring.datasource.password=password

# Docker Compose Integration
spring.docker.compose.file=compose.yml

# Sample Data Loading
runnerz.load-sample-data=true
```

### Environment Variables

You can override database settings with environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://your-host:5432/your-db
export SPRING_DATASOURCE_USERNAME=your-username
export SPRING_DATASOURCE_PASSWORD=your-password
```

## 🏗️ Project Structure

```
runnerz/
├── compose.yml                 # Docker Compose for PostgreSQL
├── runnerz/                   # Spring Boot application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/simwurbs/runnerz/
│   │   │   │       ├── Application.java
│   │   │   │       ├── run/           # Run entity & API
│   │   │   │       └── user/          # User API client
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       ├── schema.sql         # Database schema
│   │   │       └── data/
│   │   │           └── runs.json      # Sample data
│   │   └── test/                      # Test classes
│   ├── pom.xml
│   ├── mvnw
│   └── mvnw.cmd
└── README.md
```

## 📝 License

This project is licensed under the MIT License.

---
