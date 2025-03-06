![GitHub language count](https://img.shields.io/github/languages/count/souzafcharles/Java-Spring-Data-JPA-Bookstore-API)
![GitHub top language](https://img.shields.io/github/languages/top/souzafcharles/Java-Spring-Data-JPA-Bookstore-API)
![GitHub](https://img.shields.io/github/license/souzafcharles/Java-Spring-Data-JPA-Bookstore-API)
![GitHub last commit](https://img.shields.io/github/last-commit/souzafcharles/Java-Spring-Data-JPA-Bookstore-API)

# Java Spring Data JPA | Bookstore-API

***

▶️ Project developed based on a tutorial by **Michelli Brito** - [Decoder](https://www.michellibrito.com/).

***

## System Overview:

<p align="justify">
This document describes the development of the Bookstore Management System, a backend application built using the <code>Java Spring</code> framework. The system efficiently manages books, authors, publishers, and legal deposits, ensuring proper organisation, retrieval, and compliance with legal requirements. It utilises core <code>Spring</code> technologies, including <code>Spring Boot</code>, <code>Spring Data JPA</code>, and <code>Spring Validation</code>, to facilitate database interactions, enforce data integrity, and manage business logic. The API follows <code>RESTful</code> principles, providing structured endpoints for book-related operations such as registration, retrieval, and associations with publishers, authors, and legal deposits.
</p>

<p align="justify">
The system is designed with a structured relational model using <code>Spring Data JPA</code>. The core entities include <code>Book</code>, <code>Author</code>, <code>Publisher</code>, and <code>LegalDeposit</code>, each connected through well-defined relationships. A <code>Book</code> has a many-to-many relationship with <code>Author</code> and a many-to-one relationship with <code>Publisher</code>. Additionally, a <code>Book</code> has a one-to-one relationship with <code>LegalDeposit</code>, which stores the deposit code and country, ensuring compliance with national regulations. These relationships enable efficient data retrieval while maintaining integrity and consistency.
</p>

<p align="justify">
The system architecture prioritises scalability, maintainability, and flexibility. By leveraging <code>Spring Boot</code>'s dependency injection and inversion of control, the application follows a modular and loosely coupled design. <code>Spring Validation</code> enforces constraints to maintain data correctness, while <code>Spring Data JPA</code> abstracts database operations. The API adheres to <code>RESTful</code> best practices, enabling seamless integration with external services or frontend applications. This architecture provides a solid foundation for an extensible and efficient bookstore management system.
</p>

***

## Project Stack:

| Technology       | Version  | Description                                     |
|------------------|----------|-------------------------------------------------|
| 📐 IntelliJ IDEA | `2024.3` | Integrated Development Environment (IDE)        |
| ☕ Java           | `21`     | Backend programming language                    |
| 🌱 Spring Boot   | `3.4.3`  | Framework for creating Spring applications      |
| 🐦 Maven         | `3.9.9`  | Build automation and dependency management tool |
| 🐘 PostgreSQL    | `17.2`   | Relational database management system           |
| 👩‍🚀 Postman    | `11.19`  | API testing and development tool                |

***

## Dependencies:

| Dependency           | Category         | Description                                                                                                     |
|----------------------|------------------|-----------------------------------------------------------------------------------------------------------------|
| 🌐 Spring Web        | Web              | Builds web applications, including RESTful APIs, using Spring MVC. Uses Apache Tomcat as the default container. |
| 💾 Spring Data JPA   | SQL              | Facilitates database access using JPA with Spring Data and Hibernate.                                           |
| 🐘 PostgreSQL Driver | SQL              | JDBC and R2DBC driver enabling Java applications to interact with PostgreSQL databases.                         |
| ✔️ Validation        | Validation (I/O) | Enables Java Bean Validation using Hibernate Validator.                                                         |
| 🗝️ dotenv-java      | Configuration    | Loads environment variables from a `.env` file into the application, aiding in secure configuration management. |

***

# Project Requirements and Configurations:

## Entity Relationship Diagram:

![Model Relationship Diagram](https://github.com/souzafcharles/Java-Spring-Data-JPA-Bookstore-API/blob/main/src/main/resources/static/images/model-entities.png)

***

## Project Logic Layered Architecture:

![Layered Architecture](https://github.com/souzafcharles/Java-Spring-Data-JPA-Bookstore-API/blob/main/src/main/resources/static/images/logic-layered-architecture.png)

***

## Setting up `application.properties` File with PostgreSQL Configurations:

```properties
# Application name
spring.application.name=BookstoreAPI
# PostgreSQL Connection
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
# Hibernate settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

***

## Creation of the `.env` File:

- At the root of the project, create a file named `.env` to declare the environment variables required for the
  `PostgreSQL` database connection.

***

## Requirements for LoadEnvironment Class:

- **Class Purpose:**
    - Create the `LoadEnvironment` class to load environment variables from a `.env` file and set them as system
      properties.

- **Load Environment Method:**
    - **Method:** `loadEnv`
    - **Purpose:** Loads environment variables from a `.env` file and sets them as system properties.
    - **Implementation Details:**
        - Use the `Dotenv.configure().load()` method from the `io.github.cdimascio.dotenv` library to load the
          environment variables.
        - Iterate over the entries using
          `dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()))` to set each
          environment variable as a system property.

- **External Library:**
    - **Library:** `io.github.cdimascio.dotenv`
    - **Purpose:** Used to load environment variables from a `.env` file. Ensure this library is included as a
      dependency in your project's build configuration.

- **Purpose:**
    - Ensure that environment variables defined in a `.env` file are loaded and accessible as system properties
      throughout the application.

***

## Entry Point Integration:

- Call the environment loader at the project's entry point to ensure environment variables are available at runtime:

```java

@SpringBootApplication
public class BookstoreApiApplication {

    public static void main(String[] args) {
        LoadEnvironment.loadEnv();
        SpringApplication.run(BookstoreApiApplication.class, args);
    }

}
```

***