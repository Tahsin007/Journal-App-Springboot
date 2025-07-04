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
