# Chapter 4 - Spring Boot Structural Patterns and Chaining Processes

This project implements the classroom examples from the supplied Chapter 4 document.

## Topics implemented
1. Local ACID transaction
2. Business invariant
3. Payment and compensation
4. Saga orchestration
5. REST controller
6. CQS
7. CQRS
8. Idempotency
9. Retry
10. Reconciliation

## Mini-project flow
Create Order -> Make Payment -> Reserve Inventory -> Confirm Order

If inventory reservation fails after payment succeeds:
Refund Payment -> Cancel Order

## Requirements
- Java 17+
- Maven 3.9+
- Spring Boot 3.5.x

## Run
```bash
mvn spring-boot:run
```

The application runs at:
http://localhost:8080

H2 console:
http://localhost:8080/h2-console

JDBC URL:
jdbc:h2:mem:chapter4db

Username:
sa

Password:
(blank)

## Test the main Saga

### Successful order
POST http://localhost:8080/orders

JSON:
{
  "productId": 101,
  "quantity": 1,
  "amount": 1500
}

Expected status:
CONFIRMED

### Failure / compensation
POST http://localhost:8080/orders

JSON:
{
  "productId": 101,
  "quantity": 50,
  "amount": 7500
}

Expected status:
CANCELLED

The second case demonstrates:
Create Order -> Payment Success -> Inventory Failed -> Refund -> Cancel Order

## Other examples

Local ACID:
POST /chapter4/acid/orders

CQS command:
POST /chapter4/cqs/orders

CQS query:
GET /chapter4/cqs/orders/{id}

CQRS command:
POST /chapter4/cqrs/orders

CQRS query:
GET /chapter4/cqrs/orders/{id}

CQRS all:
GET /chapter4/cqrs/orders

Idempotency:
POST /chapter4/idempotency?eventId=EVT100&orderId=1

Send the same request twice. The second processing is ignored.

Retry:
POST /chapter4/retry?productId=101&quantity=50

Reconciliation:
POST /chapter4/reconcile

JSON:
{
  "status": "PAID"
}
