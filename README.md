# Honey Shop Application

Honey Shop Application is a Java Spring Boot web application for an online honey shop. 
The project allows users to browse honey products, register, log in, add products 
to a shopping cart, create orders, and view their order history. 
It also includes an admin area for managing products and order statuses.

## Technologies

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- MySQL
- Maven
- Lombok
- HTML
- CSS

## Main Features

- User registration and login
- User profile page
- Product catalog
- Shopping cart
- Order creation
- User order history
- Admin dashboard
- Product creation, update, and deactivation
- Order status management
- Default admin user initialization

## Project Structure

```text
src/main/java/app
|-- config
|-- exception
|-- model
|-- repository
|-- service
`-- web
```

- `config` contains application configuration, beans, and authentication interceptor.
- `exception` contains custom exception classes.
- `model` contains JPA entity classes and enums.
- `repository` contains Spring Data JPA repositories.
- `service` contains the business logic.
- `web` contains controllers and DTO classes.

The HTML templates are located in:

```text
src/main/resources/templates
```

Static resources are located in:

```text
src/main/resources/static
```

## Database Configuration

The application uses MySQL. The active profile is set in:

```properties
spring.profiles.active=dev
```

The development database configuration is in:

```text
src/main/resources/application-dev.properties
```

Default database settings:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/honney-shop-application?createDatabaseIfNotExist=true
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:}
```

You can configure the database credentials by setting environment variables:

```text
DB_USERNAME=root
DB_PASSWORD=replace_with_your_mysql_password
```

## Default Admin User

The application creates a default admin user on startup. The default user settings are configured in the application properties:

```properties
users.default-user.name=Galina
users.default-user.email=galina.k.georgieva@abv.bg
users.default-user.country=BULGARIA
users.default-user.password=123456789
```

## How to Run the Project

1. Make sure Java 17 is installed.
2. Make sure MySQL Server is running.
3. Configure the database username and password.
4. Open the project in IntelliJ IDEA.
5. Reload the Maven project.
6. Run the main class:

```text
app.Application
```

After the application starts, open:

```text
http://localhost:8080
```

## Maven Build

To build the project with Maven:

```bash
mvn clean compile
```

To run tests:

```bash
mvn test
```

## Repository

Public repository:

```text
https://github.com/Gal-Ii/honeyShop.git
```

## Author

Galina Georgieva
