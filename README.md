# Cab Booking

Using Spring Boot, Spring Data JPA, and MySQL, this project demonstrates entity
mapping, layered architecture (controller, service, repository), and AOP-based
logging for cross-cutting concerns.

## Tech Stack

- Java 17
- Spring Boot 3.3.5 (Web, Data JPA, Validation, AOP)
- Hibernate / JPA
- MySQL 8
- Log4j2
- Maven
- JUnit 5 + Mockito

## Features

- Get bookings by type (Shared / Personal)
- Book an available cab
- Cab availability is updated automatically on booking
- Centralized exception handling and AOP-based logging

## Getting Started

### 1. Set up the database

Run `src/main/resources/CabBookingTableScript.sql` against MySQL to create the `cab_booking_db` schema and seed data.

### 2. Configure the connection

Update `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/cab_booking_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
server.port=8765
```

### 3. Run

```powershell
.\mvnw spring-boot:run
```

The API will be available at `http://localhost:8765`.

## API Endpoints

### Get bookings by type

```
GET /api/cab/{bookingType}
```

`bookingType` is `Shared` or `Personal`.

Response:

```
[
  {
    "bookingId": 1001,
    "customerName": "Michel",
    "phoneNo": 9867542341,
    "bookingType": "Shared",
    "cabDTO": {
      "cabNo": 451678,
      "modelName": "Honda",
      "driverPhoneNo": 9823478234,
      "availability": "No"
    }
  }
]
```

### Book a cab

```
POST /api/cab
Content-Type: application/json
```

Request:

```
{
  "customerName": "Alice",
  "phoneNo": 9123456780,
  "bookingType": "Personal",
  "cabDTO": { "cabNo": 456783 }
}
```

Response (201):
Booking successful 1004

Running Tests
.\mvnw test
