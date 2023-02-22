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





# API Documentation

<!-- TABLE OF CONTENTS -->
## API Table of Contents

* [Customer API](#customer-api)
  * POST /api/users/login
  * POST /api/users/register
* [Product API](#product-api)
  * GET /api/products
  * GET /api/products/filter/{category}
* [Cart API](#cart-api)
  * GET /api/cart
  * POST /api/cart/add
  * POST /api/cart/delete
  * GET /api/cart/checkout
  * POST /api/cart/checkout

## Customer API
### Login
**You send:**  Your  login credentials.
**You get:** An `API-Token` with wich you can make further actions.

**Request:**
```
POST /api/users/login HTTP/1.1
Accept: application/json
Content-Type: application/json
```
```json
{
    "email": "foo@bar.com",
    "password": "1234567" 
}
```
**Successful Response:**
```
HTTP/1.1 200 OK
Content-Type: application/json
```
```json
{
   "token": "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NzcwNTgxNDksImV4cCI6MTY3NzA2NTM0OSwiY3VzdG9tZXJJZCI6M30.MjZ3zRUJzqxNhh10djnMDWYcxt-WViSySWYOnuxHQ10"
}
```

### Register

**Request:**
```
POST /api/users/register HTTP/1.1
Accept: application/json
Content-Type: application/json
```
```json
{
  "name": "foobar",
  "email": "foo@bar.com",
  "password": "1234567"
}
```
**Successful Response:**
```
HTTP/1.1 200 OK
Content-Type: application/json
```
```json
{
  "id": 1,
  "name": "foobar",
  "email": "foo@bar.com"
}
```

## Product API
### List Product

**Request:**
```
GET /api/products HTTP/1.1
Authorization: Bearer {token}
Accept: application/json
```
**Successful Response:**
```
HTTP/1.1 200 OK
Content-Type: application/json
```
```json
[
  {
    "id": 1,
    "name": "lenovo legion y530",
    "price": 15000000,
    "category": {
      "id": 1,
      "name": "laptop"
    },
    "seller": {
      "id": 1,
      "name": "admin"
    }
  },
  {
    "id": 2,
    "name": "acer nitro 5",
    "price": 13500000,
    "category": {
      "id": 1,
      "name": "Komputer"
    },
    "seller": {
      "id": 1,
      "name": "admin"
    }
  },
  ...
  ]
```

### List Product by Category

**Request:**
```
**GET /api/products/filter/{category}** HTTP/1.1
Authorization: Bearer {token}
Accept: application/json
Content-Type: application/json
```
**Successful Response:**
```
HTTP/1.1 200 OK
Content-Type: application/json
```
```json
[
  {
    "id": 9,
    "name": "samsung galaxy s22",
    "price": 9490000,
    "category": {
      "id": 3,
      "name": "handphone"
    },
    "seller": {
      "id": 1,
      "name": "admin"
    }
  },
  {
    "id": 10,
    "name": "iphone 14 pro",
    "price": 17760000,
    "category": {
      "id": 3,
      "name": "handphone"
    },
    "seller": {
      "id": 1,
      "name": "admin"
    }
  },
  ...
  ]
```


## License
[Apache License 2.0](https://choosealicense.com/licenses/apache-2.0/)
