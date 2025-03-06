![GitHub language count](https://img.shields.io/github/languages/count/souzafcharles/Java-Spring-Data-JPA-Bookstore-API)
![GitHub top language](https://img.shields.io/github/languages/top/souzafcharles/Java-Spring-Data-JPA-Bookstore-API)
![GitHub](https://img.shields.io/github/license/souzafcharles/Java-Spring-Data-JPA-Bookstore-API)
![GitHub last commit](https://img.shields.io/github/last-commit/souzafcharles/Java-Spring-Data-JPA-Bookstore-API)

# Java Spring Data JPA | Bookstore API

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

# Backend Requirements Specification:

## Requirements for `Book` Entity Class:

- **Entity Mapping:**
    - Define the `Book` class as an entity to represent a database table;
    - Annotate the class with `@Entity` to designate it as a persistent entity;
    - Use `@Table(name = "tb_book")` to map the entity to the database table named `tb_book`.

- **Attributes and Annotations:**
    - Declare attributes `id`, `title`, `isbn`, `pages`, `language`, `authors`, `publisher`, and `legalDeposit` to
      represent the respective database columns;
    - Annotate the `id` field with `@Id` and `@GeneratedValue(strategy = GenerationType.AUTO)` to specify it as the
      primary key with an automatically generated value;
    - Use `@Column(nullable = false, unique = true)` on `title`, `isbn`, and `language` to enforce unique and non-null
      constraints;
    - Apply `@Column(nullable = false)` on `pages` to ensure it is non-null;
    - Establish a many-to-many relationship between `Book` and `Author` using `@ManyToMany` and `@JoinTable`, specifying
      `joinColumns` and `inverseJoinColumns`;
    - Configure the many-to-one relationship with `Publisher` using `@ManyToOne` and
      `@JoinColumn(name = "publisher_id")`;
    - Configure the one-to-one relationship with `LegalDeposit` using
      `@OneToOne(mappedBy = "book", cascade = CascadeType.ALL)`.

- **Accessors and Mutators:**
    - Implement `getters` and `setters` for all attributes to facilitate data manipulation.

- **Serializable Interface:**
    - Ensure the entity implements the `Serializable` interface to enable object serialisation for transmission or
      storage.

- **Database Constraints and Relationships:**
    - Ensure that the `title`, `isbn`, and `language` columns are unique and not blank;
    - Ensure that the `pages` column enforces a non-null value;
    - Define structured associations between `Book`, `Author`, `Publisher`, and `LegalDeposit` entities through
      respective mappings.

- **Constructor:**
    - Implement a default constructor for the entity;
    - Implement a parameterised constructor accepting a `BookRequestDTO`, a `Publisher`, a `Set<Author>`, and a
      `LegalDeposit`.

- **Equals and HashCode:**
    - Override the `equals()` method to compare instances based on the `id` attribute;
    - Override `hashCode()` to generate a consistent hash code using `Objects.hashCode(id)`.

***

## Requirements for `BookRequestDTO` Record Class:

- **Record Definition:**
    - Define the `BookRequestDTO` class as a record to represent a data transfer object (DTO);
    - Ensure the class implements the `Serializable` interface to enable object serialisation for transmission or
      storage.

- **Attributes and Annotations:**
    - Declare attributes `id`, `title`, `isbn`, `pages`, `language`, `publisherId`, `authorIds`,
      `depositCodeRegistration`, and `country` to represent the respective data fields;
    - Use `@NotBlank` on `title`, `isbn`, `language`, `depositCodeRegistration`, and `country` to enforce validation
      constraints ensuring these fields are not null or empty;
    - Apply `@NotNull` on `pages`, `publisherId`, and `authorIds` to ensure these fields are not null.

- **Validation Constraints:**
    - Ensure that the `title`, `isbn`, `language`, `depositCodeRegistration`, and `country` fields are not blank;
    - Ensure that the `pages`, `publisherId`, and `authorIds` fields are not null.

- **Serializable Interface:**
    - Ensure the record implements the `Serializable` interface to enable object serialisation for transmission or
      storage.

***

## Requirements for `BookResponseDTO` Record Class:

- **Record Definition:**
    - Define the `BookResponseDTO` class as a record to represent a data transfer object (DTO);
    - Ensure the class implements the `Serializable` interface to enable object serialisation for transmission or
      storage.

- **Attributes and Annotations:**
    - Declare attributes `id`, `title`, `isbn`, `pages`, `language`, `publisherId`, `authorIds`, and `depositCode` to
      represent the respective data fields;
    - Use `Optional<UUID>` for `publisherId` to handle possible null values;
    - Use `Optional<String>` for `depositCode` to handle possible null values.

- **Constructor:**
    - Implement a constructor that accepts a `Book` entity as a parameter;
    - Map the attributes from the `Book` entity to the corresponding fields in the `BookResponseDTO` record;
    - Use `Optional.ofNullable` for nullable fields to ensure they are properly handled.

- **Serializable Interface:**
    - Ensure the record implements the `Serializable` interface to enable object serialisation for transmission or
      storage.

- **Data Mapping:**
    - Ensure that the attributes of the `Book` entity are accurately mapped to the corresponding fields in the
      `BookResponseDTO` record;
    - Handle possible null values for `publisherId` and `depositCode` using `Optional.ofNullable`.

***

## Requirements for `Author` Entity Class:

- **Entity Mapping:**
    - Define the `Author` class as an entity to represent a database table;
    - Annotate the class with `@Entity` to designate it as a persistent entity;
    - Use `@Table(name = "tb_author")` to map the entity to the database table named `tb_author`.

- **Attributes and Annotations:**
    - Declare attributes `id`, `name`, `nationality`, and `books` to represent the respective database columns;
    - Annotate the `id` field with `@Id` and `@GeneratedValue(strategy = GenerationType.AUTO)` to specify it as the
      primary key with an automatically generated value;
    - Use `@Column(nullable = false, unique = true)` on `name` to enforce unique and non-null constraints;
    - Apply `@Column(nullable = false)` on `nationality` to ensure it is non-null;
    - Establish a many-to-many relationship between `Author` and `Book` using
      `@ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)`.

- **Accessors and Mutators:**
    - Implement `getters` and `setters` for all attributes to facilitate data manipulation.

- **Serializable Interface:**
    - Ensure the entity implements the `Serializable` interface to enable object serialisation for transmission or
      storage.

- **Database Constraints and Relationships:**
    - Ensure that the `name` column is unique and not blank;
    - Ensure that the `nationality` column is not blank;
    - Define structured associations between `Author` and `Book` entities through respective mappings.

- **Constructor:**
    - Implement a default constructor for the entity.

- **Equals and HashCode:**
    - Override the `equals()` method to compare instances based on the `id` attribute;
    - Override `hashCode()` to generate a consistent hash code using `Objects.hashCode(id)`.

***

## Requirements for `Publisher` Entity Class:

- **Entity Mapping:**
    - Define the `Publisher` class as an entity to represent a database table;
    - Annotate the class with `@Entity` to designate it as a persistent entity;
    - Use `@Table(name = "tb_publisher")` to map the entity to the database table named `tb_publisher`.

- **Attributes and Annotations:**
    - Declare attributes `id`, `name`, `country`, and `books` to represent the respective database columns;
    - Annotate the `id` field with `@Id` and `@GeneratedValue(strategy = GenerationType.AUTO)` to specify it as the
      primary key with an automatically generated value;
    - Use `@Column(nullable = false, unique = true)` on `name` and `country` to enforce unique and non-null constraints;
    - Establish a one-to-many relationship between `Publisher` and `Book` using
      `@OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)`.

- **Accessors and Mutators:**
    - Implement `getters` and `setters` for all attributes to facilitate data manipulation.

- **Serializable Interface:**
    - Ensure the entity implements the `Serializable` interface to enable object serialisation for transmission or
      storage.

- **Database Constraints and Relationships:**
    - Ensure that the `name` and `country` columns are unique and not blank;
    - Define structured associations between `Publisher` and `Book` entities through respective mappings.

- **Constructor:**
    - Implement a default constructor for the entity.

- **Equals and HashCode:**
    - Override the `equals()` method to compare instances based on the `id` attribute;
    - Override `hashCode()` to generate a consistent hash code using `Objects.hashCode(id)`.

***

## Requirements for `LegalDeposit` Entity Class:

- **Entity Mapping:**
    - Define the `LegalDeposit` class as an entity to represent a database table;
    - Annotate the class with `@Entity` to designate it as a persistent entity;
    - Use `@Table(name = "tb_legal_deposit")` to map the entity to the database table named `tb_legal_deposit`.

- **Attributes and Annotations:**
    - Declare attributes `id`, `depositCode`, `country`, and `book` to represent the respective database columns;
    - Annotate the `id` field with `@Id` and `@GeneratedValue(strategy = GenerationType.AUTO)` to specify it as the
      primary key with an automatically generated value;
    - Use `@Column(nullable = false, unique = true)` on `depositCode` and `country` to enforce unique and non-null
      constraints;
    - Establish a one-to-one relationship between `LegalDeposit` and `Book` using `@OneToOne` and
      `@JoinColumn(name = "book_id")`.

- **Accessors and Mutators:**
    - Implement `getters` and `setters` for all attributes to facilitate data manipulation.

- **Serializable Interface:**
    - Ensure the entity implements the `Serializable` interface to enable object serialisation for transmission or
      storage.

- **Database Constraints and Relationships:**
    - Ensure that the `depositCode` and `country` columns are unique and not blank;
    - Define structured associations between `LegalDeposit` and `Book` entities through respective mappings.

- **Constructor:**
    - Implement a default constructor for the entity.

- **Equals and HashCode:**
    - Override the `equals()` method to compare instances based on the `id` attribute;
    - Override `hashCode()` to generate a consistent hash code using `Objects.hash(id, depositCode)`.

***

## Requirements for `Book` Repository Interface:

- **Repository Definition:**
    - Define the `BookRepository` interface to extend `JpaRepository`.
    - Annotate the interface with `@Repository` to designate it as a repository.

- **Type Parameters:**
    - Specify `Book` as the entity type and `UUID` as the primary key type.

- **Inheritance:**
    - Ensure that the interface inherits standard CRUD operations from `JpaRepository`.

***

## Requirements for `Author` Repository Interface:

- **Repository Definition:**
    - Define the `AuthorRepository` interface to extend `JpaRepository`.
    - Annotate the interface with `@Repository` to designate it as a repository.

- **Type Parameters:**
    - Specify `Author` as the entity type and `UUID` as the primary key type.

- **Inheritance:**
    - Ensure that the interface inherits standard CRUD operations from `JpaRepository`.

***

## Requirements for `Publisher` Repository Interface:

- **Repository Definition:**
    - Define the `PublisherRepository` interface to extend `JpaRepository`.
    - Annotate the interface with `@Repository` to designate it as a repository.

- **Type Parameters:**
    - Specify `Publisher` as the entity type and `UUID` as the primary key type.

- **Inheritance:**
    - Ensure that the interface inherits standard CRUD operations from `JpaRepository`.

***

## Requirements for `LegalDeposit` Repository Interface:

- **Repository Definition:**
    - Define the `LegalDepositRepository` interface to extend `JpaRepository`.
    - Annotate the interface with `@Repository` to designate it as a repository.

- **Type Parameters:**
    - Specify `LegalDeposit` as the entity type and `UUID` as the primary key type.

- **Inheritance:**
    - Ensure that the interface inherits standard CRUD operations from `JpaRepository`.

***

## Requirements for `BookService` Class:

- **Class Definition and Annotations:**
    - Define the `BookService` class as a service to provide business logic for handling books;
    - Annotate the class with `@Service` to designate it as a service layer component.

- **Autowired Repositories:**
    - Use `@Autowired` to inject the `BookRepository`, `AuthorRepository`, `PublisherRepository`, and
      `LegalDepositRepository` dependencies into the class.

- **Transactional Methods:**
    - Annotate methods with `@Transactional` to manage database transactions.

- **Create Method:**
    - Method `create(@Valid BookRequestDTO dto)`:
        - Fetch the `Publisher` entity using the `publisherId` from the DTO. If not found, throw
          `IllegalArgumentException` with the message `BookstoreMessages.PUBLISHER_NOT_FOUND`;
        - Retrieve the set of `Author` entities using the `authorIds` from the DTO. If no authors are found, throw
          `IllegalArgumentException` with the message `BookstoreMessages.AT_LEAST_ONE_AUTHOR_REQUIRED`;
        - Create a new `Book` entity using the DTO, the retrieved publisher, authors, and set `legalDeposit` to `null`;
        - Save the `Book` entity to the repository;
        - Create and save a `LegalDeposit` entity using the DTO's `depositCodeRegistration` and `country`, and associate
          it with the saved `Book` entity;
        - Update the `Book` entity with the saved `LegalDeposit` and persist the changes.

- **Read All Method:**
    - Method `readAll()`:
        - Annotate with `@Transactional(readOnly = true)` to fetch all books from the repository;
        - Map the list of `Book` entities to `BookResponseDTO` and return the list.

- **Read One Method:**
    - Method `readOne(UUID id)`:
        - Annotate with `@Transactional(readOnly = true)` to fetch a single book by its `id` from the repository;
        - If the book is not found, throw `IllegalArgumentException` with the message
          `BookstoreMessages.BOOK_NOT_FOUND`;
        - Map the `Book` entity to `BookResponseDTO` and return it.

- **Update Method:**
    - Method `update(UUID id, @Valid BookRequestDTO dto)`:
        - Fetch the existing `Book` entity by its `id` from the repository. If not found, throw
          `IllegalArgumentException` with the message `BookstoreMessages.BOOK_NOT_FOUND`;
        - Retrieve the `Publisher` entity using the `publisherId` from the DTO. If not found, throw
          `IllegalArgumentException` with the message `BookstoreMessages.PUBLISHER_NOT_FOUND`;
        - Retrieve the set of `Author` entities using the `authorIds` from the DTO. If no authors are found, throw
          `IllegalArgumentException` with the message `BookstoreMessages.AT_LEAST_ONE_AUTHOR_REQUIRED`;
        - Update the `Book` entity's attributes with the values from the DTO;
        - Update or create a `LegalDeposit` entity, associate it with the `Book` entity, and persist the changes;
        - Save the updated `Book` entity to the repository and return the mapped `BookResponseDTO`.

- **Delete Method:**
    - Method `delete(UUID id)`:
        - If the book does not exist by its `id`, throw `IllegalArgumentException` with the message
          `BookstoreMessages.BOOK_NOT_FOUND`;
        - Delete the book by its `id` from the repository.

- **Helper Methods:**
    - Include private helper methods to facilitate entity retrieval and validation as necessary.

- **Exception Handling:**
    - Ensure that exceptions thrown are appropriate and carry meaningful messages for failed operations.

***

## Requirements for `BookController` Class:

- **Class Definition and Annotations:**
    - Define the `BookController` class to handle HTTP requests related to books;
    - Annotate the class with `@RestController` to designate it as a RESTful web service;
    - Use `@RequestMapping("/bookstore/books")` to set the base URL path for book-related endpoints;
    - Apply `@CrossOrigin(origins = "*", maxAge = 3600)` to allow cross-origin requests.

- **Autowired Service:**
    - Use `@Autowired` to inject the `BookService` dependency into the class.

- **Create Endpoint:**
    - Endpoint: `@PostMapping`:
        - Define a method `create(@RequestBody BookRequestDTO dto)` to handle HTTP POST requests for creating a new
          book;
        - Return a `ResponseEntity<BookResponseDTO>` with HTTP status `HttpStatus.CREATED` and the created book data.

- **Read All Endpoint:**
    - Endpoint: `@GetMapping`:
        - Define a method `readAll()` to handle HTTP GET requests for retrieving all books;
        - Return a `ResponseEntity<List<BookResponseDTO>>` with HTTP status `HttpStatus.OK` and the list of all books.

- **Read One Endpoint:**
    - Endpoint: `@GetMapping(value = "/{id}")`:
        - Define a method `readOne(@PathVariable UUID id)` to handle HTTP GET requests for retrieving a single book by
          its `id`;
        - Return a `ResponseEntity<BookResponseDTO>` with HTTP status `HttpStatus.OK` and the requested book data.

- **Update Endpoint:**
    - Endpoint: `@PutMapping(value = "/{id}")`:
        - Define a method `update(@PathVariable UUID id, @RequestBody BookRequestDTO dto)` to handle HTTP PUT requests
          for updating an existing book;
        - Return a `ResponseEntity<BookResponseDTO>` with HTTP status `HttpStatus.OK` and the updated book data.

- **Delete Endpoint:**
    - Endpoint: `@DeleteMapping(value = "/{id}")`:
        - Define a method `delete(@PathVariable UUID id)` to handle HTTP DELETE requests for deleting an existing book
          by its `id`;
        - Return a `ResponseEntity<Void>` with HTTP status `HttpStatus.NO_CONTENT`.

- **HTTP Status Codes:**
    - Ensure that the correct HTTP status codes are used for each operation:
        - `HttpStatus.CREATED` (201) for successful creation;
        - `HttpStatus.OK` (200) for successful retrieval and updates;
        - `HttpStatus.NO_CONTENT` (204) for successful deletion.

***

## Requirements for `BookstoreMessages` Utility Class:

- **Class Definition and Annotations:**
    - Define the `BookstoreMessages` class as a utility class to store constant message strings;
    - Use `private` constructor and throw `IllegalStateException` with the message "Utility class" to prevent
      instantiation.

- **Book Messages:**
    - Declare the following public static final string constants:
        - `BOOK_NOT_FOUND`: "Book not found";
        - `BOOK_SAVED_SUCCESSFULLY`: "Book saved successfully!".

- **Publisher Messages:**
    - Declare the following public static final string constant:
        - `PUBLISHER_NOT_FOUND`: "Publisher not found".

- **Author Messages:**
    - Declare the following public static final string constant:
        - `AT_LEAST_ONE_AUTHOR_REQUIRED`: "At least one author is required".

- **Best Practices:**
    - Ensure that all string constants are publicly accessible and final to prevent modification;
    - Use descriptive and meaningful names for constants to reflect the associated messages;
    - Maintain a clear separation of concerns by grouping messages based on their context (e.g., book, publisher,
      author).
