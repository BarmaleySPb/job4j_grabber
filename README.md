# job4j_grabber

[![Build Status](https://app.travis-ci.com/BarmaleySPb/job4j_grabber.svg?branch=master)](https://app.travis-ci.com/BarmaleySPb/job4j_grabber)
[![codecov](https://codecov.io/gh/BarmaleySPb/job4j_grabber/branch/master/graph/badge.svg?token=2J6FQFQ290)](https://codecov.io/gh/BarmaleySPb/job4j_grabber)

### Описание
Проект представляет собой парсер вакансий. Парсинг вакансий осуществляется с сайта: https://www.sql.ru/.
Распарсенные вакансии сохраняются в БД PostgreSQL.

Система запускается по расписанию, (данная функция реализована с помощью планировщика заданий Quartz), т. е. по истечению каждого периода запуска парсятся 5 страницы сайта вакансий и с помощью JDBC складируются в БД.<br>
Период запуска указывается в настройках - в файле app.properties (параметр time).
Доступ к интерфейсу сайта осуществляется через REST API с помощью библиотеки Jsoup (парсинг HTML).

### Технологии
- Java 16
- JDBC
- PostgreSQL
- Jsoup
- Планировщик заданий Quartz
- Sockets
- Maven
- JaCoCo
- Travis CI
- SLF4J (in progress...)

## Контактные данные:
[![Telegram](https://img.shields.io/badge/-telegram-grey?style=flat&logo=telegram&logoColor=white)](https://t.me/Evgeny_Zakharov)&nbsp;
[![Email](https://img.shields.io/badge/@%20email-005FED?style=flat&logo=mail&logoColor=white)](mailto:e.g.zakharov@gmail.com)&nbsp;