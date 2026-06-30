# Enviro365 Investments Backend

A Spring Boot REST API for Enviro365 Investments that manages investor portfolios, processes withdrawal notices, and generates CSV withdrawal reports.

## Features

### Investor Management
- Create new investors with associated investment products
- Retrieve a single investor's portfolio by ID
- Retrieve a list of all investors

### Withdrawal Management
- Process withdrawal requests with business rule validation
- Retrieve full withdrawal history
- Export withdrawal statements as CSV

## Business Rules
- Retirement product withdrawals are only permitted for investors older than 65
- Withdrawal amount cannot exceed the available product balance
- Withdrawal amount cannot exceed 90% of the product balance
- Invalid requests return appropriate error messages via global exception handling

## Advanced Features Implemented
- DTO layer 
- Global exception handling 
- Input validation on request bodies
  
## Tech Stack
- Java 17
- Spring Boot 4.x
- Spring Web (REST APIs)
- Spring Data JPA
- H2 in-memory database
- 
## API Endpoints
- `POST /api/investors`
- `GET /api/investors/{id}`
- `GET /api/investors/all`
- `POST /api/withdrawals`
- `GET /api/withdrawals/history`
- `GET /api/withdrawals/{investorId}/export`

## Setup
1. Clone the repository
2. Run `mvn clean install`
3. Run the application — H2 database initializes automatically
4. API available at `http://localhost:8080`
5. H2 console available at `http://localhost:8080/h2-console`

## Testing
Endpoints were tested using Postman during development.

## Frontend
Built with Angular — [View Frontend Repository](https://github.com/nompiloMalinga/enviro365-frontend)
