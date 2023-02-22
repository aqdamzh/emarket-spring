# Emarket Spring Boot Backend

 An online store application.

## Table of contents
<!--ts-->
   * [Tittle](#emarket-spring-boot-backend)
   * [Table of contents](#table-of-contents)
   * [Installation](#installation)
   * [Configuration](#configuration)
      * [Programming Language](#programming-language)
      * [Maven](maven)
   * [Dependencies Used](#dependencies-used)
<!--te-->

## Installation

Clone this repository and import into **Maven**

```bash
git clone https://github.com/aqdamzh/emarket-spring.git
```

## Configuration
### Programming Language:
- java
### Maven

## Dependencies Used
### Spring Web
The **Spring Web** Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.  <br />
[more about Spring Web](https://spring.io/guides/gs/serving-web-content/)
### JDBC API
**JDBC API** is Database Connectivity API that defines how a client may connect and query a database. <br />
[more about JDBC API](https://spring.io/guides/gs/relational-data-access/)
### PostgreSQL Driver
**PostgreSQL Driver** is A JDBC and R2DBC driver that allows Java programs to connect to a PostgreSQL database using standard, database independent Java code. <br />
[PostgreSQL Driver](https://spring.io/guides/gs/relational-data-access/)
### JSON Web Token
JSON Web Token Support For The JVM. <br />
[JSON Web Token](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt)

# API Documentation Example


## Login
**You send:**  Your  login credentials.
**You get:** An `API-Token` with wich you can make further actions.

**Request:**
```json
POST /api/users/login HTTP/1.1
Accept: application/json
Content-Type: application/json

{
    "email": "foo@bar.com",
    "password": "1234567" 
}
```
**Successful Response:**
```json
HTTP/1.1 200 OK
Content-Type: application/json

{
   "apitoken": "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NzcwNTgxNDksImV4cCI6MTY3NzA2NTM0OSwiY3VzdG9tZXJJZCI6M30.MjZ3zRUJzqxNhh10djnMDWYcxt-WViSySWYOnuxHQ10"
}
```


## License
[Apache License 2.0](https://choosealicense.com/licenses/apache-2.0/)
