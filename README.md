# SOSSpringBoot

SOSSpringBoot is a sample Spring Boot 3 application that demonstrates REST APIs, Spring Data JPA, MySQL integration, validation, actuator endpoints, and JWT-based authentication.

## Technology stack

- Java: 21
- Maven: 3.6+
- Spring Boot: 3.3.5
- Spring Framework: 6.x (managed by Spring Boot)
- MySQL: configured through MySQL Connector/J (`com.mysql:mysql-connector-j`)
- JPA/Hibernate: Spring Data JPA with Hibernate
- Validation: Spring Boot Starter Validation
- JWT: JJWT 0.12.6
- Actuator: Spring Boot Actuator
- Packaging: executable JAR

## Prerequisites

Before running the application, make sure you have:

- JDK 21 installed
- MySQL server running
- Maven installed

## Database setup

1. Create a MySQL database (for example, `demo_ors`).
2. Update the database connection details in `src/main/resources/application.properties`.
3. Optionally run the SQL script in `src/main/resources/db.sql` to create the initial table structure.

Example configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/demo_ors?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata
spring.datasource.username=root
spring.datasource.password=
```

## Run the application

```bash
mvn clean install
java -jar target/SOSSpringBoot-0.0.1-SNAPSHOT.jar
```

The application runs on port `8080` by default.

## API endpoints

Base URL: `http://localhost:8080`

### Marksheet APIs

- `GET /marksheet` - Get all marksheets
- `GET /marksheet/{id}` - Get marksheet by ID
- `GET /marksheet/rollno/{rollNo}` - Get marksheet by roll number
- `GET /marksheet/meritlist` - Get merit list
- `POST /marksheet` - Create a new marksheet
- `POST /marksheet/search` - Search marksheets with optional pagination
- `PUT /marksheet/{id}` - Update marksheet fully
- `PATCH /marksheet/{id}` - Update marksheet partially
- `DELETE /marksheet/{id}` - Delete marksheet

Example request:

```bash
curl http://localhost:8080/marksheet
```

### User APIs

- `GET /user` - Get all users
- `GET /user/{id}` - Get user by ID
- `GET /user/login/{loginId}` - Get user by login ID
- `GET /user/email/{email}` - Get user by email
- `POST /user` - Create a new user
- `POST /user/login` - Authenticate and receive a JWT token
- `POST /user/search` - Search users with optional pagination
- `POST /user/changePassword/{id}` - Change password
- `PUT /user/{id}` - Update user fully
- `PATCH /user/{id}` - Update user partially
- `DELETE /user/{id}` - Delete user

Example request:

```bash
curl http://localhost:8080/user
```

### Role APIs

- `GET /role` - Get all roles
- `GET /role/{id}` - Get role by ID
- `GET /role/name/{name}` - Get role by name
- `POST /role` - Create a new role
- `POST /role/search` - Search roles with optional pagination
- `PUT /role/{id}` - Update role fully
- `PATCH /role/{id}` - Update role partially
- `DELETE /role/{id}` - Delete role

Example request:

```bash
curl http://localhost:8080/role
```

### Document APIs

- `GET /Document` - Health check
- `GET /Document/get/{id}` - Get document by ID
- `POST /Document/search` - Search documents
- `POST /Document/search/{page}` - Search documents with pagination
- `POST /Document/save` - Create or update document metadata
- `POST /Document/upload` - Upload a document (multipart form, field name `file`)
- `GET /Document/download/{id}` - Download document content by ID
- `GET /Document/delete/{id}` - Delete document by ID

Example request:

```bash
curl http://localhost:8080/Document
```

## JWT authentication

The application uses JWT (JSON Web Token) for protecting API access.

### How it works

1. Call `POST /user/login` with a valid `loginId` and `password`.
2. The server returns a token in the response body.
3. For all protected APIs, send the token in the `Authorization` header:

```http
Authorization: Bearer <token>
```

### Login example

```bash
curl -X POST http://localhost:8080/user/login \
  -H "Content-Type: application/json" \
  -d '{"loginId":"admin","password":"password"}'
```

### Token configuration

JWT settings are defined in `src/main/resources/application.properties`:

```properties
jwt.secret=SunilOSSecretKey2024#SpringBoot!!
jwt.expiration=86400000
```

> The `jwt.secret` value should be changed before using this project in production.

## Technical notes

- A custom interceptor (`FrontCtl`) validates the JWT on every protected request.
- Public endpoints include `/user/login` and `/actuator/**`; CORS preflight (`OPTIONS`) requests are also allowed through.
- Marksheet, Role, and User controllers return a JSON response envelope with `error`, `message`, and `data` fields.
- Validation errors are returned with detailed field-level messages.
- Marksheet, Role, and User data access is implemented through a custom EntityManager-based DAO layer (`BaseDAOInt`/`BaseDAOImpl`), not Spring Data repositories. Document data access still uses a Spring Data JPA repository directly.

## Reference

For more information, refer to the project source code in the `src` folder and the Spring Boot documentation.

