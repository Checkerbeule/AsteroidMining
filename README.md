# 🚀 AsteroidMining API – A Clean Code Showcase

Welcome to the Space Mining API! This project serves as a pragmatic demonstration of modern Java development using Spring Boot.
It is specifically designed to showcase how to apply **SOLID Principles** and **Clean Code** strategies in a real-world-inspired scenario.

> [!NOTE] **Project Status**<br>
> This is a **work-in-progress** educational project. It is intentionally not "feature-complete" as it serves as a continuous **learning playground**.

### 🎯 Purpose of this Project

This repository is a pattern example for **Junior Developers** and a portfolio piece for **Recruiters**.
Instead of over-engineering, it follows the **KISS** (Keep It Simple, Stupid/Staightforward) principle while maintaining high architectural standards.

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

### 🤝 Join the Discussion!

Are you a **Junior Developer**? I invite you to explore the Project! I've added detailed explanations of the principles used in this project. If something is unclear, feel free to open an issue or start a discussion.

Are you a **Senior or Recruiter**? I’d love to hear your feedback!
 - Could the DIP be applied more strictly?
 - Is the KISS approach appropriate here?
 - How would you scale the MiningMarketRepository?

Let's learn and grow together. Feel free to Fork, Star, or Contribute!
