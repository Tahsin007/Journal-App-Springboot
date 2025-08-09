# Journal App

This is a Spring Boot application for a journaling app. It provides a REST API for users to create, read, update, and delete journal entries. It also includes user authentication and authorization features.

## Features

*   User registration and login with JWT authentication
*   CRUD operations for journal entries
*   Admin functionality to view all users
*   API documentation with Swagger

## Technologies Used

*   **Java 17**
*   **Spring Boot**
    *   Spring Web
    *   Spring Data MongoDB
    *   Spring Security
    *   Spring Data Redis
*   **MongoDB**
*   **Redis**
*   **JJwt** (for JWTs)
*   **Lombok**
*   **Maven**
*   **Swagger/OpenAPI** (for API documentation)

## Folder Architecture

```
.
├───.gitattributes
├───.gitignore
├───mvnw
├───mvnw.cmd
├───pom.xml
├───README.md
├───.git/...
├───.idea/...
├───.mvn/
│   └───wrapper/
│       └───maven-wrapper.properties
├───src/
│   ├───main/
│   │   ├───java/
│   │   │   └───com/
│   │   │       └───tahsinProject/
│   │   │           └───demo/
│   │   │               ├───Car.java
│   │   │               ├───DemoApplication.java
│   │   │               ├───Engine.java
│   │   │               ├───controller/
│   │   │               │   ├───AdminController.java
│   │   │               │   ├───JournalEntryController.java
│   │   │               │   ├───PublicController.java
│   │   │               │   └───UserController.java
│   │   │               ├───entity/
│   │   │               │   ├───JournalEntry.java
│   │   │               │   └───User.java
│   │   │               ├───filters/
│   │   │               │   └───JwtAuthFilter.java
│   │   │               ├───repository/
│   │   │               │   ├───JournalEntryRepository.java
│   │   │               │   └───UserRepository.java
│   │   │               ├───security/
│   │   │               │   ├───SecurityConfig.java
│   │   │               │   ├───SwaggerConfig.java
│   │   │               │   └───UserConfig.java
│   │   │               ├───service/
│   │   │               │   ├───JournalEntryService.java
│   │   │               │   ├───UserDetailsServiceImpl.java
│   │   │               │   └───UserService.java
│   │   │               └───utils/
│   │   │                   └───JwtUtils.java
│   │   └───resources/
│   │       ├───application-dev.yml
│   │       ├───application-prod.yml
│   │       └───application.yml
│   └───test/
│       └───java/
│           └───com/
│               └───tahsinProject/
│                   └───demo/
│                       └───DemoApplicationTests.java
└───target/
    ├───classes/...
    ├───generated-sources/...
    ├───generated-test-sources/...
    ├───maven-archiver/...
    ├───maven-status/...
    ├───surefire-reports/...
    └───test-classes/...
```

## API Endpoints

### Public Endpoints

*   `POST /public/signUp`: Create a new user.
*   `POST /public/login`: Authenticate a user and get a JWT token.

### User Endpoints

*   `GET /user/id/{myId}`: Get a user by their ID.
*   `DELETE /user`: Delete the authenticated user.
*   `PUT /user`: Update the authenticated user's information.

### Journal Entry Endpoints

*   `GET /journal`: Get all journal entries for the authenticated user.
*   `POST /journal`: Create a new journal entry.
*   `GET /journal/{entryId}`: Get a journal entry by its ID.
*   `DELETE /journal/{journalId}`: Delete a journal entry by its ID.
*   `PUT /journal/{myId}`: Update a journal entry by its ID.

### Admin Endpoints

*   `GET /admin/allUsers`: Get a list of all users (admin only).

## Getting Started

### Prerequisites

*   Java 17
*   Maven
*   MongoDB
*   Redis

### Installation

1.  Clone the repository:
    ```sh
    git clone https://github.com/your-username/Journal-App-Springboot.git
    ```
2.  Navigate to the project directory:
    ```sh
    cd Journal-App-Springboot
    ```
3.  Install the dependencies:
    ```sh
    mvn install
    ```

### Configuration

The application configuration is in the `src/main/resources` directory. There are three configuration files:

*   `application.yml`: Base configuration.
*   `application-dev.yml`: Development-specific configuration.
*   `application-prod.yml`: Production-specific configuration.

You can set the active profile in `application.yml` or by using environment variables.

### Running the Application

You can run the application using the following command:

```sh
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.

## Usage

You can use a tool like Postman or curl to interact with the API. The API documentation is available at `http://localhost:8080/swagger-ui.html`.