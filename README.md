# Order Service

Сервис заказов с конечным автоматом состояний и идемпотентным созданием через `Idempotency-Key`.

## Архитектурные решения
- Явные разрешённые переходы `NEW → CONFIRMED/CANCELLED → SHIPPED`.
- Уникальный ключ идемпотентности хранится в БД, повтор запроса возвращает существующий заказ.
- Транзакционная бизнес-логика отделена от HTTP-адаптера.

## Запуск и тесты
`mvn spring-boot:run` · `mvn verify`

`POST /api/orders` с заголовком `Idempotency-Key`, телом `{"customerId":"customer-42"}`. Статус меняется через `PATCH /api/orders/{id}/status?to=CONFIRMED`.

Java 21 · Spring Boot 3 · JPA · H2/PostgreSQL · JUnit 5 · Mockito
