# 🚀 AsteroidMining API – A Clean Code Showcase with AI integration
![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen?style=for-the-badge&logo=springboot)
![Spring AI](https://img.shields.io/badge/Spring%20AI-Mistral-red?style=for-the-badge&logo=mistralai&logoColor=red)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18.1-blue?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Container-blue?style=for-the-badge&logo=docker&logoColor=white)

Welcome to the Space Mining API! This project serves as a pragmatic demonstration of modern Java development using Spring Boot.
It is specifically designed to showcase how to apply **SOLID Principles** and **Clean Code** strategies in a real-world-inspired scenario.

[!NOTE]
> **Project Status**<br>
> This is a **work-in-progress** educational project. It is intentionally not "feature-complete" as it serves as a continuous **learning playground**.

### 🎯 Purpose of this Project
This repository is a pattern example for **Junior Developers**, a portfolio piece for **Recruiters** and a **playground** for new technologies like LLM integration.
Instead of over-engineering, it follows the **KISS** (Keep It Simple, Stupid/Staightforward) principle while maintaining high architectural standards.

### 🤖 Smart Asteroid Generation (LLM Integration)
One of the core highlights is the **AI powered** creation of new asteroid instances. The API can generate asteroids either locally or by leveraging a Large Language Model (Mistral AI via Spring AI).</br>
The following principles were applied to ensure a stable integration:
- **Dynamic Steering:** Uses randomized themes (e.g. *volcanic, botanic, gazy, radioactive*) to guide the AI's creativity.
- **Validation & Fallbacks:** Implements a validation layer to handle "hallucinations" or malformed AI responses and provides a fallback strategy.
- **Strategy Pattern:** Uses `@ConditionalOnProperty` to switch between `Local-`, `Hybrid-` and `FullAI-` generation modes at runtime.

### 🛠 Applied Clean Code & SOLID Principles
Throughout the codebase, you will find extensive Javadoc explaining why certain patterns were chosen:

#### S.O.L.I.D. Implementation:
 - **SRP**: Each layer (Controller, Service, Repository) has a single, clear responsibility.
 - **ISP & DIP**: Repository interfaces decouple business logic from data storage.
 - **LSP**: In-memory implementations can be swapped with real databases without breaking the system.
#### Additional Clean Code Principles
 - **KISS** Principle: We avoid "Interface-Hell" by using concrete service classes where abstractions don't yet add value.
 - **Immutability**: Using Java Records and Enums to ensure thread safety and data integrity. 

### 🏛 Architecture & Design Approach
The project follows a **3-layer architecture**, focusing on the core domain while applying pragmatic engineering standards.
Instead of starting with the database, the code is centered around the **Business Domain** (Asteroid, RiskProfile, ResourceType).

The technical layers are structured as follows:
 - **Web Layer**: REST Controllers handling HTTP and JSON mapping.
 - **Service Layer**: The "Brain" of the app, orchestrating business rules and exception signaling.
 - **Persistence Layer**: Repositories managing asteroid and market data.

### 🛡️ Security & Best Practices
While this is a demo project, it follows production-ready standards for data protection and configuration:
 - SSL/TLS Encryption: The REST API is secured via HTTPS to demonstrate secure communication.
 - Secret Management: Sensitive data like Keystore passwords and keypair are never hardcoded. The project uses Environment Variables to show professional configuration handling.

### 🐳 Infrastructure & Persistence
To keep the project flexible, it supports two persistence modes:
 - **In-Memory** Mode: Default mode for rapid development and testing without external dependencies.
 - **PostgreSQL** and **Docker**: Uses a Dockerized PostgreSQL database for persistent storage.

### 🚀 Getting Started
You can run the project either locally for development or fully containerized.
There are a lot of inline comments explaining what to do and how to get the app running.</br>
Prerequisites:
 - **Java 21** or higher
 - **Maven**
 - **Docker or Docker Desktop** (if you like to use PostgreSQL or run the API with docker)
 - **SSL Certificate**: Since the API is secured via HTTPS, a Keystore (PKCS12) is required. Follow the instructions in application.yaml to generate your own keypair or disable SSL.
 - **Environment Variables**: Create a .env file in the root directory to manage your passwords. See .env.example for further instructions.

#### Option 1: Quick Start (In-Memory repository, no docker)
Run the app without any external dependencies straight from your IDE. No docker required.

1. Run the App in your IDE: Use the IntelliJ SpringBootNoDbRunConfig 
2. The system will automatically inject In-Memory Repositories.

#### Option 2: Full Docker Setup (Database + API)
When you have docker installed and would like to see the project in a "production-like" environment you can run these commands:

    # Build the JAR and the Docker image using the docker profile
    mvn clean package -DskipTests -Pdocker
    # Start the environment
    docker compose up -d

#### Option 3: Hybrid Development (Docker DB + IDE)

Best for active coding with real DB connection. Run the database in Docker and the app in your IDE:

1. Start the database: docker compose up -d db
2. Run the app in your IDE: Use the IntelliJ SpringBootPostgresRunConfig.

After startup your can test the API locally at https://localhost/swagger-ui/index.html

### 🤝 Join the Discussion!

Are you a **Junior Developer**? I invite you to explore the Project! I've added detailed explanations of the principles used in this project. If something is unclear, feel free to open an issue or start a discussion.

Are you a **Senior or Recruiter**? I’d love to hear your feedback!

Let's learn and grow together. Feel free to Fork, Star, or Contribute!
