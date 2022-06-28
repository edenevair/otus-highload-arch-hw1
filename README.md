# Домашние задания для курса Otus Highload Architect

## Задание 1
Заготовка для социальной сети

### Постановка задачи

**Цель:**

В результате выполнения ДЗ вы создадите базовый скелет социальной сети, который будет развиваться в дальнейших ДЗ.

**Описание/Пошаговая инструкция выполнения домашнего задания:**

Требуется разработать создание и просмотр анект в социальной сети.

**Функциональные требования:**

- Авторизация по паролю.
- Страница регистрации, где указывается следующая информация:
    - Имя
    - Фамилия
    - Возраст
    - Пол 
    - Интересы 
    - Город
- Страницы с анкетой.

**Нефункциональные требования:**

- Любой язык программирования
- В качестве базы данных использовать MySQL
- Не использовать ORM
- Программа должна представлять из себя монолитное приложение.
- Не рекомендуется использовать следующие технологии:
  - Репликация
  - Шардинг
  - Индексы
- Кэширование Фронт необязателен. 
- Сделать инструкцию по локальному запуску приложения, приложить Postman-коллекцию. 

**Требования:**

Есть возможность регистрации, создавать персональные страницы, возможность подружиться, список друзей.
Отсутствуют SQL-инъекции.
Пароль хранится безопасно.

### Реализация

**Требования:**

- установленная JDK 11
- Docker

**Инструкция по запуску приложения:**

1. Запуск БД в Docker контейнере командой.
```
docker run --name otus-mysql-hw1 -d -p 33306:3306 \
  -e MYSQL_USER=otus_user \
  -e MYSQL_PASSWORD=12345678 \
  -e MYSQL_ROOT_PASSWORD=pa$$w0rd \
  -e MYSQL_DATABASE=hw1 \
   mysql:8.0.23
```
Создаст БД доступную по ``jdbc:mysql://localhost:33306/hw1``
2. Дождаться запуска БД и готовности принимать запросы
3. В корневом каталоге проекта выполнить команду:
```
./gradlre bootRun
```
4. В браузере перейти по ссылке http://localhost:8080 и дождаться перехода на страницу авторизации

**Инструкция по использованию приложения:**

Работа с приложением возможна через пользовательский UI, доступный по адресу http://localhost:8080

В данном случае доступны такие опции как:

- авторизаци по паре логин/пароль
- регистрация польщователя
- просмотр профиля пользователя со списком друзей
- просмотр списка пользователей
- добавление пользователей в друзья
- принятие/отклоенение заявок на добавление в друзья

Так же возможна работа напрямую с сервисов путем выполнения Rest-запросов непосредственно к API.

В приложенном [файле](Otus Highload Arcchitect HW.postman_collection.json) можно найти коллекцию запросов для Postman.

Для выполнения запросов требуется создать нового пользователя (в теле запроса в примере указан json-объект для создания пользователя), а затем выполнить метод Login указав логин и пароль указанные при авторизации.

Список городов заполняется автоматически при старте приложения, список доступных городов и их системных идентификаторов можно посмотреть в API.