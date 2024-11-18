# 🍋 Citronix - Lemon Farm Management System

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Citronix is a comprehensive farm management system designed specifically for lemon farms, enabling efficient tracking of production, harvesting, and sales processes.

## 📝 API Endpoints

### Farm Management

```http
GET     /api/v1/farms                # Get all farms
GET     /api/v1/farms/{id}           # Get farm by ID
POST    /api/v1/farms                # Create new farm
PUT     /api/v1/farms/{id}           # Update farm
DELETE  /api/v1/farms/{id}           # Delete farm
GET     /api/v1/farms/search         # Search farms by criteria
```

### Field Management

```http
GET     /api/v1/fields                # Get all fields
GET     /api/v1/fields/{id}           # Get field by ID
POST    /api/v1/fields                # Create new field
PUT     /api/v1/fields/{id}           # Update field
DELETE  /api/v1/fields/{id}           # Delete field
```

### API Documentation UI
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI Spec: `http://localhost:8080/v3/api-docs`

## Class Diagram
![class_diagramme.png.png](docs/class_diagramme.png.png)

## 🚀 Getting Started

### Prerequisites
- Java 17
- Maven
- Your favorite IDE (IntelliJ IDEA recommended)

### Installation Steps
1. Clone the repository:
```bash
git clone https://github.com/yourusername/Citronix.git
```

2. Navigate to the project directory:
```bash
cd Citronix
```

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```