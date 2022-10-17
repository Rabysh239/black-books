### HW5:
#### ВНИМАНИЕ
> _Это задание как и все последующие будет итеративно обогащать результат полученный в предыдущем домашнем задании_


> Работа должна быть оформлена следующим образом:
>- каждая домашняя работа должна быть оформлена как отдельный **PULL REQUEST** в репозитории.
>- в корневой папке проекта должен находиться файл readme.md, который будет содержать описание сервиса, инструкции по запуску, 
ссылка на главный класс, скриншоты критических этапов работы 
>- ресурсы по заданиям (скриншоты, диаграммы и т.п.) должны распологаться в подпапках ’resources’ папок с выполненными заданиями
#### Задания:
> Реализовать микросервисное приложение Бук кроссинг. Не всегда можно раздобыть бумажный экземпляр интересующей книжки 
> и/или хочется прочитать что-неожиданное.
- Бэк владельца книг / участника обмена -  книги и их параметры , куда отправились и т.п.
- книжный шкаф  - бэк обменников - есть емкость, сбор статы, количество книг в одни руки
- книжный магазин / библиотека - сбор данных о популярных книгах, сбор данных о их обороте 

__Используя сервис полученный на предыдущем шаге, необходимо реализовать следующее:__

1. Добавить Swagger и с тэгом системные добавить эндпоинты 
    - /system/liveness [Spring Probs](https://spring.io/blog/2020/03/25/liveness-and-readiness-probes-with-spring-boot)
    - /system/readiness 
    - /system/version

   __Сделать отдельный @RestController и в нём используя WebClient отдавать [Mono](https://www.baeldung.com/spring-5-webclient)__

2. Для сервисов добавить сущности и для каждой сущности реализовать методы CRUD (Create/Read(по айди и список)/Update/Delete)

 
#### BookHunter Backend:
 - Необходимо завести сущность пользователь 
> Поля сущности в вашем приложении могут отличаться от представленного ниже json, 
> однако они должны отдаваться приложением именно в таком виде
```json
UserProfile {
  "nick": "Book Overloard",
  "name": "Vasya Pupkin",
  "age": "22",
  "gender": "hidden",
  "location": "21.30001803537425, -157.8219935651796"
}
```

 - Необходимо добавить метод
```java
@GetMapping("user/{id}/nearest")
public Flux<BookHunter> getNearest(
        @PathVariable UUID bookHunterId, 
        @RequestParam(required = false, defaultValue = "100") Long distance,
        @RequestParam(required = false, defaultValue = "50") Long amount,
){...}
```
Который будет возвращать {amount} ближайших пользователей в радиусе {distance} к пользователю с указанным {id}

- Необходимо добавить метод
```java
@GetMapping("user/nearest")
public Flux<BookHunter> getNearest(
        @RequestParam Double latitude,
        @RequestParam Double longitude,
        @RequestParam(required = false, defaultValue = "100") Long distance,
        @RequestParam(required = false, defaultValue = "50") Long amount,
){...}
```
Который будет возвращать {amount} ближайших пользователей в радиусе {distance} к пользователю с указанным координатам 
определяемым {latitude} и {longitude}

#### BookShelf Backend:
- Необходимо завести сущность шкаф
> Поля сущности в вашем приложении могут отличаться от представленного ниже json,
> однако они должны отдаваться приложением именно в таком виде
```json
Depository {
  "nick": "Blue Telephone Booth",
  "address": "3rd Floor, International House, 1 St Katharine's Way, London E1W 1UN, Великобритания",
  "description": "first door on the left",
  "type": "bookshelf",
  "location": "51.50756071944658, -0.0737618204547709"
}
```

- Необходимо добавить метод
```java
@GetMapping("shelf/{id}/nearest")
public Flux<BookDeposit> getNearest(
        @PathVariable UUID bookDepositId, 
        @RequestParam(required = false, defaultValue = "100") Long distance,
        @RequestParam(required = false, defaultValue = "50") Long amount,
){...}
```
Который будет возвращать {amount} ближайшие хранилища книг в радиусе {distance} к хранилищу с указанным {id}

- Необходимо добавить метод
```java
@GetMapping("shelf/nearest")
public Flux<BookDeposit> getNearest(
        @RequestParam Double latitude,
        @RequestParam Double longitude,
        @RequestParam(required = false, defaultValue = "250") Long distance,
        @RequestParam(required = false, defaultValue = "10") Long amount,
){...}
```
Который будет возвращать {amount} ближайшие хранилища в радиусе {distance} к указанным координатам определяемым {latitude} и {longitude} 
переданными в параметрах запроса

#### BlackBooks Backend:

- Необходимо завести сущность транзацкия и сущности из других сервисов
> Поля сущности в вашем приложении могут отличаться от представленного ниже json,
> однако они должны отдаваться приложением именно в таком виде
```json
Transaction {
  "user": "Book Overloard",
  "shelf": "3rd Floor, International House, 1 St Katharine's Way, London E1W 1UN, Великобритания",
  "timestampt": "2022-09-08 11:00:22.000000",
  "action": "returnal"
}
```

- Необходимо добавить метод
```java
@GetMapping("transactions/")
public Flux<BookDeposit> getNearest(
        @RequestParam(required = false) UUID bookDepositId,
        @RequestParam(required = false) UUID bookHunterId,
        @RequestParam(required = false, defaultValue = "10") Long amount,
        @RequestParam(required = false, name = "sortBy", defaultValue = "desc") SortType type,
){...}
```
Который будет возвращать {amount} список транзакций для {bookDepositId} и  {bookHunterId} отсортированный по {type = [asc, desc]}

 > Списки можно хранить в памяти сервисов и заполнять в рантайме перед инициализацией, методы должны возвращать 
 > не менее 5 результатов для значений приведенных Вами в Ваших Readme.md


### Ресурсы для самостоятельного ознакомления
 - [SpringDoc](https://springdoc.org/)
 - [Documenting a Spring REST API Using OpenAPI 3.0](https://www.baeldung.com/spring-rest-openapi-documentation)
 - [Guide to Spring 5 WebFlux](https://www.baeldung.com/spring-webflux)
 - [WebClient](https://www.baeldung.com/spring-5-webclient)
   