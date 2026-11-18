# Order Service

> **Lab / not maintained.** Это ранний учебный проект: репозиторий заархивирован и оставлен для истории. Актуальные проекты — в [профиле](https://github.com/Asyl-k).
>
> **Lab / not maintained.** This is an early learning project. The repository is archived and kept for history; see [my profile](https://github.com/Asyl-k) for maintained projects.

Сервис заказов с конечным автоматом состояний и идемпотентным созданием через `Idempotency-Key`.

## Архитектурные решения
- Явные разрешённые переходы `NEW → CONFIRMED/CANCELLED → SHIPPED`.
- Уникальный ключ идемпотентности хранится в БД, повтор запроса возвращает существующий заказ.
- Транзакционная бизнес-логика отделена от HTTP-адаптера.

## Запуск и тесты
`mvn spring-boot:run` · `mvn verify`

`POST /api/orders` с заголовком `Idempotency-Key`, телом `{"customerId":"customer-42"}`. Статус меняется через `PATCH /api/orders/{id}/status?to=CONFIRMED`.

Java 21 · Spring Boot 3 · JPA · H2/PostgreSQL · JUnit 5 · Mockito
