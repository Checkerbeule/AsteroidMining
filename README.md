# 🚀 AsteroidMining API – A Clean Code Showcase

Welcome to the Space Mining API! This project serves as a pragmatic demonstration of modern Java development using Spring Boot.
It is specifically designed to showcase how to apply <B>SOLID Principles</B> and <B>Clean Code</B> strategies in a real-world-inspired scenario.

### 🎯 Purpose of this Project

This repository is a pattern example for <B>Junior Developers</B> and a portfolio piece for <B>Recruiters</B>.
Instead of over-engineering, it follows the <B>KISS</B> (Keep It Simple, Stupid/Staightforward) principle while maintaining high architectural standards.

### 🛠 Applied Clean Code & SOLID Principles

Throughout the codebase, you will find extensive Javadoc explaining why certain patterns were chosen:

#### S.O.L.I.D. Implementation:
 - <b>SRP</B>: Each layer (Controller, Service, Repository) has a single, clear responsibility.
 - <B>ISP & DIP</B>: Repository interfaces decouple business logic from data storage.
 - <B>LSP</B>: In-memory implementations can be swapped with real databases without breaking the system.
#### Additional Clean Code Principals
 - <B>KISS</B> Principle: We avoid "Interface-Hell" by using concrete service classes where abstractions don't yet add value.
 - <B>Immutability</B>: Using Java Records and Enums to ensure thread safety and data integrity. 

### 🏛 Architecture & Design Approach
The project follows a <b>3-layer architecture</b>, focusing on the core domain while applying pragmatic engineering standards.
Instead of starting with the database, the code is centered around the <B>Business Domain</B> (Asteroid, RiskProfile, ResourceType).

The technical layers are structured as follows:
 - <B>Web Layer</B>: REST Controllers handling HTTP and JSON mapping.
 - <B>Service Layer</B>: The "Brain" of the app, orchestrating business rules and exception signaling.
 - <B>Persistence Layer</B>: Repositories managing asteroid and market data.

### 🤝 Join the Discussion!

Are you a <B>Junior Developer</B>? I invite you to explore the Project! I've added detailed explanations of the design patterns used. If something is unclear, feel free to open an issue or start a discussion.

Are you a <B>Senior or Recruiter</B>? I’d love to hear your feedback!
 - Could the DIP be applied more strictly?
 - Is the KISS approach appropriate here?
 - How would you scale the MiningMarketRepository?

Let's learn and grow together. Feel free to Fork, Star, or Contribute!
