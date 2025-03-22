# User Microservice README
This microservice provides a simple implementation of CRUD (Create, Read, Update, Delete) operations for managing user data.
The service is designed to interact with a PostgreSQL database and exposes RESTful endpoints for client applications.

**Table of Contents**
- Prerequisites
- Setup
- Endpoints
- Contributing
## Prerequisites
### PostgreSQL Database: 
Ensure you have a PostgreSQL database set up and accessible via a JDBC driver.

### Java Development Kit (JDK):
JDK 17 or later.
## Setup
### Clone the Repository:
> git clone https://github.com/walidg-dev/user.git

### Configure Database:
Create a PostgreSQL database and a user with the necessary privileges.

### Define the following environment variables :

- DB_HOST = your database host
- DB_NAME=your database name, by default : postgres
- DB_USER=your_username, by default : postgres
- DB_PASSWORD=your_password
### Build the Project:
> ./gradlew build
### Run the Service:
> ./gradlew bootRun

## Endpoints
### Create User
- Method: POST
- Path: /api/v1/users
- Description: Create a new user.
- Request Body:
{
"firstName": "John",
"lastName": "Doe",
"email": "John@email.com"
}
- Example:
> curl -i -X POST http://localhost:8081/api/v1/users \
-d '{"firstName" : "John", "lastName": "Doe",
  "email": "John@email.com"}'
### Read User by ID
- Method: GET
- Path: /api/v1/users/{id}
- Description: Retrieve a user by their ID.
- Example:
> curl -i -X GET http://localhost:8081/api/v1/users/1 
### Read All Users
- Method: GET
- Path: /api/v1/users/all
- Description: Retrieve all users.
- Example:
> curl -i -X GET http://localhost:8081/api/v1/users/all 
### Update User
- Method: PUT
- Path: /api/v1/users/{id}
- Description: Update an existing user.
Request Body:
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "John.Doe@email.com"
}
- Example:
> curl -i -X PUT http://localhost:8081/api/v1/users/1 \
-d '{
"firstName": "John", "lastName": "Doe", "email": "John.Doe@email.com"}'
### Delete User
- Method: DELETE
- Path: /api/v1/users/{id}
- Description: Delete a user by their ID.
- Example:
> curl -i -X DELETE http://localhost:8081/api/v1/users/1 
## Contributing
Contributions are welcome! Please follow these guidelines:

### Fork the repository.
Create a new branch for your feature or bug fix.
Commit your changes and push to your fork.
Open a pull request against the main repository.
For more details, see the Contributing Guidelines.

### License
This project is licensed under the MIT License. See the LICENSE file for details.