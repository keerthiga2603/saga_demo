# Chapter 4 Experiment Notes

## Aim
To implement and demonstrate structural patterns and chaining processes using Spring Boot.

## Procedure
1. Create a Spring Boot project with Spring Web, Spring Data JPA and H2.
2. Create Order and Product entities.
3. Create JPA repositories.
4. Implement a local ACID transaction using @Transactional.
5. Implement the inventory business invariant so stock never becomes negative.
6. Implement payment and refund compensation.
7. Implement Saga orchestration.
8. Expose the Saga through a REST controller.
9. Implement CQS by separating command and query methods.
10. Implement CQRS with separate command and query services.
11. Implement idempotent event handling.
12. Implement retry for transient inventory failures.
13. Implement reconciliation for intermediate order states.
14. Run the application and test the endpoints.
15. Verify successful confirmation and failed-order compensation.

## Expected successful result
An order with sufficient stock reaches CONFIRMED.

## Expected failure result
When stock is insufficient after payment succeeds, the payment is refunded and the order becomes CANCELLED.

## Concept mapping
Local ACID -> @Transactional
Business invariant -> InventoryService
Eventual consistency -> Independent Saga steps
Idempotency -> PaymentEventConsumer
Retry -> RetryService
CQS -> OrderCqsService
CQRS -> OrderCommandService / OrderQueryService
Saga -> SagaService
Compensation -> refund + CANCELLED
Reconciliation -> ReconciliationService
