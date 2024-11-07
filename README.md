# Spring Boot JWT Authentication example with Spring Security & Spring Data JPA
## User Registration, User Login and Authorization process.
The diagram shows flow of how we implement User Registration, User Login and Authorization process.

![spring-boot-jwt-authentication-security-flow](https://github.com/user-attachments/assets/afaf3fb4-c4df-4204-959a-30a01773f028)

## Spring Boot Server Architecture with Spring Security
You can have an overview of our Spring Boot Server with the diagram below:

![spring-boot-jwt-authentication-security-architecture](https://github.com/user-attachments/assets/f9b4b128-7437-44d9-9df1-d0148dc13d0d)

## Dependency
– If you want to use MySQL:
```xml
<dependency>
  <groupId>com.mysql</groupId>
  <artifactId>mysql-connector-j</artifactId>
  <scope>runtime</scope>
</dependency>
```

## Configure Spring Datasource, JPA, App properties
Open `src/main/resources/application.properties`
- For MySQL
```
spring.datasource.url=jdbc:mysql://localhost:3306/userdb?useSSL=false
spring.datasource.username=root
spring.datasource.password=password@123

spring.jpa.hibernate.ddl-auto=update

# App Properties
application.security.jwt.secret-key=${JWT_SECRET_KEY:t+HcmvXQ9cbpK+5raB1e+8ZUTc6lNygAyhWb0kpRIiM=}
application.security.jwt.expiration=${JWT_EXPIRATION:86400000}
application.security.jwt.refresh-token.expiration=${JWT_REFRESH_EXPIRATION:604800000}
```
## Run Spring Boot application
```
mvn spring-boot:run
```
