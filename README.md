# Тестовое задание

Здесь представлена основная информация по проекту. Более детальная документация каждого commit в задании храниться в
файле README ветки его задания.

* Основная ветка - main, в ней создан проект. Каждое из дальнейших заданий выполнены в своей ветке.
* Используется архитектурный стиль REST для построения API
* Используется json для запросов и ответов в API
* Сборка проекта осуществляется через maven

## Задание 1. Базовая реализация REST API - ветка test1

### Легенда

У компании “РомашкаКо” есть большой ассортимент различных товаров. Они хотят, чтобы пользователи могли через их интернет
магазин знакомится с ассортиментом. Для этого они попросили тебя создать небольшой REST API для работы с товарами.

### Функциональные требования

1. Использовать технологию Spring (Spring Boot) через maven.
2. Товары хранить во внутренней коллекции.
3. С помощью JUnit покрыть тестами часть функционала
4. Унифицированные ошибки - реализация json ошибок

### Бизнес требования

1. Заказчик хочет получить полный CRUD по товарам:
    1. Получать список товаров
    2. Получать отдельный товар
    3. Создавать товар
    4. Изменять товар
    5. Удалять товар
2. Товар должен содержать следующие поля:
    1. Название товара
    2. Описание товара
    3. Цена товара
    4. В наличии ли товар
3. Должны выполняться проверки на ограничения полей:
    1. Название товара ограничено 255 символами и оно обязательно при создании
    2. Описание товара ограничено 4096 символами
    3. Цена товара не может быть меньше 0, по умолчанию 0
4. Если при создании не указано наличие товара явно, то по умолчанию товар не в наличии

## Задание 2. Подключение базы данных - ветка test2

### Легенда

Случился скачок электроэнергии в результате чего сервер интернет магазина “МоиТовары” компании “РомашкаКо”
перезагрузился.
При перезагрузке заново запустилось твоё приложение и оказалось, что вся информация по товарам,
которую компания добавила через твой api потерялась. Заказчик оказался не доволен и попросил тебя решить проблему
с сохранением данных.

### Функциональные требования

1. Использовать СУБД Postgres
2. Если в СУБД нет таблиц, то их создание происходит при запуске приложения
3. Должны быть ограничения на столбцы в соответствии требованиями из задания 1
4. Использовать sql миграции для создания таблиц (например: liquibase, flyway и тд.)

### Бизнес требования

1. При перезапуске приложения, данные не должны теряться

## Задание 3. Использование Docker - ветка test3

### Легенда

Прошло какое-то время и оказалось, что сервер компании “РомашкаКо” для интернет-магазина уже не справляется с нагрузкой
и было решено закупить более мощный сервер. После покупки сервера и переноса базы и приложения,
при запуске твоего приложения возникают ошибки. Заказчику не нравится, что при каждом обновлении системы
или железа нужно вызвать тебя для настройки приложения и он предлагает решить тебе эту задачу.

### Функциональные требования

1. Использовать Docker.
2. Убрать СУБД postgres в Docker
3. Для запуска использовать docker-compose.
4. Настроить сборку проекта, чтобы автоматизировать шаги создания образа.

### Бизнес требования

1. Приложение должно запускаться на различных конфигурациях сервера (ОС, различная конфигурация ОС, различный набор
   вспомогательных программ).

## Задание 4. Добавление фильтрации и сортировки - ветка test4

### Легенда

Прошло немного времени и интернет магазин “МоиТовары” стал популярный. Стало появляться больше товаров и пользователям
стало сложнее искать нужное. Тогда компания “РомашкаКо” решает добавить на сайт поиск товаров, а также выводить
пользователям
первые N товаров по запросу и просит тебя помочь доработать api.

### Функциональные требования

1. Фильтрация и сортировка через параметры запроса
2. Валидация значений фильтрации и сортировки
3. Фильтры и сортировки можно применять вместе
4. Написаны JUnit тесты на сортировку и фильтрацию

### Бизнес требования

1. Должны уметь фильтровать:
    1. по названию или части названия товара
    2. по цене товара, а также больше или меньше заданной цены
    3. по наличию товара
2. Нужно уметь сортировать:
    1. по имени товара
    2. по цене товара
3. Ограничивать выборку заданным количеством записей, которое передает пользователь

## Задание 5. Добавление продаж через сайт - ветка test5

### Легенда

Многим пользователям понравится ассортимент в интернет магазине “МоиТовары” и они хотели бы покупать товар через сайт.
Компания “РомашкаКо” решает добавить такую возможность и опять на помощь призывает тебя.

### Функциональные требования

1. Покрыть JUnit тестами новые эндпоинты

### Бизнес требования

1. Добавить CRUD для новой сущности “Поставка товара”. Данный документ говорит сколько товара поставили для продажи.
2. Поля сущности “Поставка товара”:
    1. Название документа
    2. Товар, который поставили для продажи
    3. Количество поставленного товара
3. Ограничения полей сущности “Поставка товара”:
    1. Название документа ограниченно 255 символами
    2. Товар должен быть существующим
    3. Количество товара в документе должно быть больше 0
4. Аналогично добавить CRUD для новой сущности “Продажа товара”. Данный документ говорит сколько товара было продано
5. Поля сущности “Продажа товара”:
    1. Название документа
    2. Товар, который был продан
    3. Количество проданного товара
6. Ограничение полей сущности “Продажа товара”:
    1. Название документа ограниченно 255 символами
    2. Товар должен существовать
    3. Количество товара в документе должно быть больше 0
    4. Нельзя продавать в минус по количеству товара
7. В документе “Продажа товара” добавить поле стоимость покупки
8. В товаре менять поле в наличии, при изменении количества товара или выводить количество товара.