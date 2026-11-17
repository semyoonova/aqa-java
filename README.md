# Full-Stack Test Automation Framework (Java)

[![Java CI with Maven](https://github.com)](https://github.com)
[![Allure Report](https://shields.io)](https://github.io)

Гибридный сквозной фреймворк автоматизации тестирования веб-платформы **Conduit (RealWorld)**. Проект объединяет в одном репозитории контрактное тестирование REST API, браузерные UI-сценарии и прямую валидацию состояния реляционной базы данных.

📊 **[Открыть актуальный интерактивный отчёт Allure (GitHub Pages)](https://github.io)**

---

## 🛠 Технологический стек

* **Core:** Java 21, Maven, JUnit 5
* **UI Automation:** Selenide (Chrome, Headless mode)
* **API Testing:** REST Assured, Jackson (сериализация/десериализация DTO)
* **Database:** H2 Database, JDBC (`Connection`, `PreparedStatement`)
* **Test Data & Config:** JavaFaker, Owner (типизированное управление `.properties`)
* **Reporting & Logging:** Allure Report (`allure-junit5`, `allure-restassured`, `allure-selenide`), AspectJ
* **Infrastructure & CI/CD:** Docker Compose, GitHub Actions, GitHub Pages

---

## 🏗 Архитектура и ключевые инженерные решения

Проект спроектирован по многослойной архитектуре с разделением ответственности, полностью исключающей дублирование кода и хардкод тестовых данных:

### 1. Слой API (`api/`)
* **Изолированные API-клиенты (`UserClient`, `ArticleClient`):** Вся логика формирования HTTP-запросов, заголовков авторизации и базовых эндпоинтов инкапсулирована в клиентах. Поддерживается динамическое переключение между локальным Docker-стендом и удалённым окружением.
* **Модели данных (DTO):** Запросы и ответы маппятся в строгие Java-объекты с использованием Lombok и Jackson (`@JsonIgnoreProperties`), что обеспечивает безопасность типов при проверках.
* **Параметризация и негативные сценарии:** Валидация граничных значений и бизнес-правил реализована через `@ParameterizedTest` и `@MethodSource`.

### 2. Слой UI (`ui/`)
* **Page Object Model (POM) с дженериками:** Создан базовый абстрактный класс `BasePage<T extends BasePage<T>>`, обеспечивающий единый контракт загрузки страниц (`isLoaded()`) и поддержку цепочек вызовов (Fluent Interface).
* **Устойчивые локаторы:** Использование динамических CSS-селекторов с привязкой к контексту контейнеров (например, изоляция элементов шапки `nav.navbar`) и умных ожиданий Selenide без использования `Thread.sleep`.

### 3. Слой Базы Данных (`utils/DbHelper`)
* **Прямая SQL-валидация (JDBC):** Реализованы методы проверки сохранения сущностей в таблицах `users` и `articles` после выполнения API-запросов.
* **Изоляция файловых блокировок:** Для безопасного параллельного чтения базы H2 из запущенного Docker-контейнера в среде Linux CI внедрено атомарное снятие слепка файла БД (`Files.copy`) с отключением блокировок (`ACCESS_MODE_DATA=r;FILE_LOCK=NO`) и синхронной записью транзакций (`WRITE_DELAY=0`).

### 4. Конфигурация и генерация данных (`utils/`)
* **Управление окружением:** Все URL, настройки браузера и креды БД вынесены в `config.properties` и читаются через типизированный интерфейс `AppConfig` (библиотека Owner).
* **Изоляция тестовых данных:** `TestDataGenerator` на базе JavaFaker генерирует уникальных пользователей и статьи для каждого запуска, гарантируя отсутствие зависимости тестов друг от друга.

---

## 🚀 Локальный запуск

### Предварительные требования
* Установленные **JDK 21+**, **Maven** и **Docker Desktop** (или Docker Engine).

### Шаги запуска
1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com
   cd aqa-java
   ```
2. Поднимите локальный бэкенд и базу данных в Docker:
   ```bash
   docker compose up -d
   ```
3. Запустите все тесты (API, UI и БД):
   ```bash
   mvn clean test
   ```
4. *(Опционально)* Для запуска браузера в фоновом режиме без GUI:
   ```bash
   mvn clean test -Dselenide.headless=true
   ```
