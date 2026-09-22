# Order–Payment–Inventory Saga (Programs 1–5)

A single Spring Boot project covering the first five programs from the Chapter 4
document, plus a small web page so the workflow can be demonstrated in a browser.

| Program | Concept | Class |
| --- | --- | --- |
| 1 | Local ACID transaction | `service/OrderService.java` |
| 2 | Business invariant (stock never negative) | `service/InventoryService.java` |
| 3 | Payment and compensation | `service/PaymentService.java` |
| 4 | Saga orchestration | `service/SagaService.java` |
| 5 | REST controller | `controller/OrderController.java` |

Supporting classes: `model/Order.java`, `model/Product.java`,
`repository/OrderRepository.java`, `repository/ProductRepository.java`,
`config/DataLoader.java` (seeds stock), `controller/ViewController.java`
(read-only endpoints used by the web page).

## Requirements

- Java 17 or newer
- Maven (or open the folder in IntelliJ / Eclipse / VS Code, which will handle the build)

## Run it

```bash
cd saga-demo
mvn spring-boot:run
```

Then open <http://localhost:8080>.

The H2 database is in memory, so nothing needs installing and the data resets on
every restart. The H2 console is at <http://localhost:8080/h2-console> with JDBC
URL `jdbc:h2:mem:sagadb`, user `sa`, and no password.

## What to demonstrate

Two products are created at startup, each with 10 units in stock.

**Success path** — order 1 unit of product 101. Payment succeeds, stock is
available, and the order comes back `CONFIRMED` with stock reduced to 9.

**Compensation path** — order 50 units. Payment still succeeds, but the
inventory step refuses to reserve because that would take stock below zero. The
saga then refunds the payment and the order comes back `CANCELLED`, with stock
unchanged.

Watch the terminal while doing this — `PaymentService` prints
`Payment successful: Rs.…` and, on the failure path, `Payment refunded: Rs.…`.

## Testing without the web page

```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"productId":101,"quantity":1,"amount":1500}'
```

Successful response:

```json
{"id":1,"productId":101,"quantity":1,"amount":1500.0,"status":"CONFIRMED"}
```

Failure and compensation response (quantity larger than stock):

```json
{"id":2,"productId":101,"quantity":50,"amount":75000.0,"status":"CANCELLED"}
```

## Endpoints

| Method | Path | Purpose |
| --- | --- | --- |
| POST | `/orders` | Runs the saga (Program 5) |
| GET | `/orders` | Lists orders — used by the web page |
| GET | `/products` | Lists products and stock — used by the web page |
| POST | `/reset` | Clears orders and restores stock to 10 |

## Why the order is what it is

Each step commits on its own, so there is no single transaction spanning the
whole workflow. That is why step 3 failing cannot roll back step 2 — the payment
is already committed, and the only way to undo it is a compensating action
(the refund). Between the payment committing and the refund completing, the
payment and order records disagree; that gap is eventual consistency.
