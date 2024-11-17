Here's the content formatted as a `.md` file that you can directly use. Just copy and paste this into your README.md file:

# 🍋 Citronix - Lemon Farm Management System

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Citronix is a comprehensive farm management system designed specifically for lemon farms, enabling efficient tracking of production, harvesting, and sales processes.

## 📝 API Endpoints

### Farm Management

```http
GET     /api/farms
GET     /api/farms/{id}
POST    /api/farms
PUT     /api/farms/{id}
DELETE  /api/farms/{id}
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
