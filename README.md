# ProductApp

Застосунок для перегляду продуктів з використанням REST API.

## Технології
- Retrofit + OkHttp — HTTP клієнт
- Moshi — парсинг JSON
- Paging 3 — пагінація
- Room — локальна база даних
- Jetpack Compose — UI
- MVVM + Clean Architecture

## Запуск
1. Клонуй репозиторій: `git clone https://github.com/твій_нік/ProductApp.git`
2. Відкрий проєкт в Android Studio
3. Запусти на емуляторі або реальному пристрої
4. Потрібне підключення до інтернету

## API
Використовується [DummyJSON](https://dummyjson.com/products)
- `GET /products` — список продуктів з пагінацією
- `GET /products/search?q=term` — пошук продуктів