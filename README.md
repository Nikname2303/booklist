Book List API
This project is a RESTful API built with Spring Boot for managing a list of books. 
It allows users to perform CRUD (Create, Read, Update, Delete) operations on a book entity stored in an H2 embedded database.
## Features

1. **CRUD Operations**: 
    - **Create a book**: Add a new book to the database.
    - **Retrieve books**: Get a list of all books or a specific book by its ID.
    - **Update a book**: Update the information of an existing book.
    - **Delete a book**: Remove a book from the database by its ID.

2. **Validation**:
    - The book title and author cannot be empty.
    - The publication year cannot exceed the current year.
    - The ISBN must be unique and contain exactly 13 characters.

3. **Database**:
    - Uses an embedded H2 database to store book information.

4. **Search and Pagination** :
    - Search books by author, title, or genre.
    - Pagination for the list of books (20 books on page).

5. **Testing**:
    - Simple unit tests using JUnit and MockMVC to verify API functionality.

## Technologies Used

- **Spring Boot version 3.3.3** - Framework for building the API.
- **Spring Data JPA** - For database interactions.
- **Lombok version 1.18** - for simplify the code.
- **MapStruct version 1.5.5** - for mapping enteties;
- **H2 Database** - Embedded database for storing books.
- **JUnit & MockMVC** - For testing the API.

## Prerequisites

- Java 21
- Maven

## API Endpoints

### 1. Create a new book
   - **Endpoint**: `POST /api/books`
   - **Request Body**: JSON containing book details.
   - **Example**:
     ```json
     {
       "title": "Dune",
       "author": "F.Gerbert",
       "publicationYear": "1965-08-01",
       "genre": "Fantasy",
       "isbn": "9780134685991"
     }
     ```
     
### 2. Retrieve all books
   - **Endpoint**: `GET /api/books`
   - **Optional Pagination**: Use query parameters `page` pagination (e.g., `/api/books?page=0`).

### 3. Retrieve a book by ID
   - **Endpoint**: `GET /api/books/{id}`
   
### 4. Update a book
   - **Endpoint**: `PUT /api/books/{id}`
   - **Request Body**: JSON containing updated book details.

### 5. Delete a book
   - **Endpoint**: `DELETE /api/books/{id}`

### 6. Search books
   - **Endpoint**: `GET /api/books/search?author=...&title=...&genre=...`
   - **Query Parameters**: `author`, `title`, `genre`.
