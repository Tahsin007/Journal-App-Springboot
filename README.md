# Journal App

A simple journal application built with Spring Boot.

## Features

### User Management
- **Sign Up:** `POST /public/signUp` - Create a new user account.
- **Login:** `POST /public/login` - Authenticate and receive a JWT token.
- **Update User:** `PUT /user` - Update your user information.
- **Delete User:** `DELETE /user` - Delete your user account.
- **Get All Users (Admin):** `GET /admin/allUsers` - Retrieve a list of all users.

### Journal Entries
- **Get All Entries:** `GET /journal` - Retrieve all of your journal entries.
- **Create Entry:** `POST /journal` - Create a new journal entry.
- **Get Entry by ID:** `GET /journal/{entryId}` - Retrieve a specific journal entry.
- **Delete Entry:** `DELETE /journal/{journalId}` - Delete a journal entry.
- **Update Entry:** `PUT /journal/{myId}` - Update a journal entry.

    * User Authentication:
        * Secure user registration and login functionality.
        * Utilizes JSON Web Tokens (JWT) for authentication, ensuring that only authorized users can access
          their journal entries.
    * Journal Entry Management:
        * Users can create, view, update, and delete their journal entries.
        * Each journal entry is associated with the logged-in user, ensuring data privacy.
    * User Profile Management:
        * Users can view and update their profile information.
        * Users can also delete their accounts.
    * Administrator Tools:
        * An admin-specific endpoint to view all registered users in the application.
    * API:
        * A well-defined RESTful API for all application functionalities.

  Technologies Used:
    * Backend: Java, Spring Boot, Spring Security, Spring Data MongoDB
    * Database: MongoDB
    * Authentication: JWT (JSON Web Tokens)
    * Build Tool: Maven
