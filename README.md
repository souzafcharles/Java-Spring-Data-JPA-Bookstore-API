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
This document describes the development of the <code>Bookstore API</code>, a backend application built using the <code>Java Spring</code> framework. The system efficiently manages books, authors, publishers, and legal deposits, ensuring proper organisation, retrieval, and compliance with legal requirements. It utilises core <code>Spring</code> technologies, including <code>Spring Boot</code>, <code>Spring Data JPA</code>, and <code>Spring Validation</code>, to facilitate database interactions, enforce data integrity, and manage business logic. The API follows <code>RESTful</code> principles, providing structured endpoints for book-related operations such as registration, retrieval, and associations with publishers, authors, and legal deposits.
</p>

<p align="justify">
The system is designed with a structured relational model using <code>Spring Data JPA</code>. The core entities include <code>Book</code>, <code>Author</code>, <code>Publisher</code>, and <code>LegalDeposit</code>, each connected through well-defined relationships. A <code>Book</code> has a many-to-many relationship with <code>Author</code> and a many-to-one relationship with <code>Publisher</code>. Additionally, a <code>Book</code> has a one-to-one relationship with <code>LegalDeposit</code>, which stores the deposit code and country, ensuring compliance with national regulations. These relationships enable efficient data retrieval while maintaining integrity and consistency.
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

## SQL Script for Populating Database Tables:

````sql
-- Drop tables if they exist with CASCADE
DROP TABLE IF EXISTS tb_book_author CASCADE;
DROP TABLE IF EXISTS tb_legal_deposit CASCADE;
DROP TABLE IF EXISTS tb_book CASCADE;
DROP TABLE IF EXISTS tb_author CASCADE;
DROP TABLE IF EXISTS tb_publisher CASCADE;

-- Create tables
CREATE TABLE tb_publisher (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    country VARCHAR(50) NOT NULL
);

CREATE TABLE tb_author (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    nationality VARCHAR(50) NOT NULL
);

CREATE TABLE tb_book (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    isbn VARCHAR(13) NOT NULL UNIQUE,
    pages INTEGER NOT NULL,
    language VARCHAR(50) NOT NULL,
    publisher_id UUID REFERENCES tb_publisher(id) ON DELETE CASCADE
);

CREATE TABLE tb_legal_deposit (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    deposit_code VARCHAR(255) NOT NULL UNIQUE,
    country VARCHAR(50) NOT NULL,
    book_id UUID REFERENCES tb_book(id) ON DELETE CASCADE
);

CREATE TABLE tb_book_author (
    book_id UUID REFERENCES tb_book(id) ON DELETE CASCADE,
    author_id UUID REFERENCES tb_author(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, author_id)
);

-- Insert publishers
INSERT INTO tb_publisher (name, country) VALUES
('Companhia das Letras', 'Brazil'),
('George Allen & Unwin', 'United Kingdom'),
('Shinchosha', 'Japan'),
('Editorial Oveja Negra', 'Colombia'),
('Random House', 'Chile'),
('Heinemann', 'Nigeria'),
('Penguin Books', 'Russia'),
('McClelland & Stewart', 'Canada'),
('Iletişim Yayınları', 'Turkey'),
('Europa Editions', 'Italy');

-- Insert books
INSERT INTO tb_book (title, isbn, pages, language, publisher_id) VALUES
('Dom Casmurro', '9788525410922', 448, 'Portuguese', (SELECT id FROM tb_publisher WHERE name = 'Companhia das Letras')),
('The Hobbit', '9780048231887', 310, 'English', (SELECT id FROM tb_publisher WHERE name = 'George Allen & Unwin')),
('Norwegian Wood', '9780375704024', 296, 'Japanese', (SELECT id FROM tb_publisher WHERE name = 'Shinchosha')),
('One Hundred Years of Solitude', '9780307389732', 417, 'Spanish', (SELECT id FROM tb_publisher WHERE name = 'Editorial Oveja Negra')),
('The House of the Spirits', '9780374530075', 433, 'Spanish', (SELECT id FROM tb_publisher WHERE name = 'Random House')),
('Things Fall Apart', '9780385474542', 209, 'English', (SELECT id FROM tb_publisher WHERE name = 'Heinemann')),
('War and Peace', '9780140447934', 1296, 'Russian', (SELECT id FROM tb_publisher WHERE name = 'Penguin Books')),
('The Handmaid''s Tale', '9780771008795', 311, 'English', (SELECT id FROM tb_publisher WHERE name = 'McClelland & Stewart')),
('My Name is Red', '9780375707976', 417, 'Turkish', (SELECT id FROM tb_publisher WHERE name = 'Iletişim Yayınları')),
('The Lying Life of Adults', '9781609455910', 324, 'Italian', (SELECT id FROM tb_publisher WHERE name = 'Europa Editions'));

-- Insert authors
INSERT INTO tb_author (name, nationality) VALUES
('Machado de Assis', 'Brazilian'),
('J.R.R. Tolkien', 'British'),
('Haruki Murakami', 'Japanese'),
('Gabriel Garcia Marquez', 'Colombian'),
('Isabel Allende', 'Chilean'),
('Chinua Achebe', 'Nigerian'),
('Leo Tolstoy', 'Russian'),
('Margaret Atwood', 'Canadian'),
('Orhan Pamuk', 'Turkish'),
('Elena Ferrante', 'Italian');

INSERT INTO tb_legal_deposit (deposit_code, country, book_id) VALUES
('BR-1899-741011', 'Brazil', (SELECT id FROM tb_book WHERE title = 'Dom Casmurro')),
('UK-1937-147012', 'United Kingdom', (SELECT id FROM tb_book WHERE title = 'The Hobbit')),
('JP-1987-852013', 'Japan', (SELECT id FROM tb_book WHERE title = 'Norwegian Wood')),
('CO-1967-369014', 'Colombia', (SELECT id FROM tb_book WHERE title = 'One Hundred Years of Solitude')),
('CL-1982-951015', 'Chile', (SELECT id FROM tb_book WHERE title = 'The House of the Spirits')),
('NG-1958-159016', 'Nigeria', (SELECT id FROM tb_book WHERE title = 'Things Fall Apart')),
('RU-1869-791017', 'Russia', (SELECT id FROM tb_book WHERE title = 'War and Peace')),
('CA-1985-634018', 'Canada', (SELECT id FROM tb_book WHERE title = 'The Handmaid''s Tale')),
('TR-1998-967019', 'Turkey', (SELECT id FROM tb_book WHERE title = 'My Name is Red')),
('IT-2020-259020', 'Italy', (SELECT id FROM tb_book WHERE title = 'The Lying Life of Adults'));

-- Associate books with authors
INSERT INTO tb_book_author (book_id, author_id) VALUES
((SELECT id FROM tb_book WHERE title = 'Dom Casmurro'), (SELECT id FROM tb_author WHERE name = 'Machado de Assis')),
((SELECT id FROM tb_book WHERE title = 'The Hobbit'), (SELECT id FROM tb_author WHERE name = 'J.R.R. Tolkien')),
((SELECT id FROM tb_book WHERE title = 'Norwegian Wood'), (SELECT id FROM tb_author WHERE name = 'Haruki Murakami')),
((SELECT id FROM tb_book WHERE title = 'One Hundred Years of Solitude'), (SELECT id FROM tb_author WHERE name = 'Gabriel Garcia Marquez')),
((SELECT id FROM tb_book WHERE title = 'The House of the Spirits'), (SELECT id FROM tb_author WHERE name = 'Isabel Allende')),
((SELECT id FROM tb_book WHERE title = 'Things Fall Apart'), (SELECT id FROM tb_author WHERE name = 'Chinua Achebe')),
((SELECT id FROM tb_book WHERE title = 'War and Peace'), (SELECT id FROM tb_author WHERE name = 'Leo Tolstoy')),
((SELECT id FROM tb_book WHERE title = 'The Handmaid''s Tale'), (SELECT id FROM tb_author WHERE name = 'Margaret Atwood')),
((SELECT id FROM tb_book WHERE title = 'My Name is Red'), (SELECT id FROM tb_author WHERE name = 'Orhan Pamuk')),
((SELECT id FROM tb_book WHERE title = 'The Lying Life of Adults'), (SELECT id FROM tb_author WHERE name = 'Elena Ferrante'));
````

***