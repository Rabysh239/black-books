# BlackBooks 
## 1 шаг
1. 
Добавил эндпоинты в [SystemController](src/main/java/ru/tinkoff/academy/blackbooks/controller/SystemController.java)  
Добавил Swagger:
```
http://localhost:8080/swagger-ui.html
```
2. 
Реализовал сущность [BookTransaction](src/main/java/ru/tinkoff/academy/blackbooks/model/BookTransaction.java). Добавил CRUD-эндпоинты в [BookTransactionController](src/main/java/ru/tinkoff/academy/blackbooks/controller/BookTransactionController.java).
Туда же добавил эндпоинт <b>/transaction/transactions</b> из задания.  
Немного замечаний к себе: из-за поджимающего дедлайна многое не успел правильно оформить:
1. Т.к. работа с другими сервисами пока не входит в задание, то в местах работы с сущностями других сервисов были реализованы не очень правильные заглушки, которые тем не менее позволят протестировать данный сервис.


## 2 шаг
Добавил тесты для предыдущего шага


## 3 шаг
Добавлен эндпоинт <b>/api/discovery</b> получения данных от внешних сервисов и тесты к нему.
```
curl -X GET localhost:8080/api/discovery
```
Результат:  
```
{
  "BookDeposit": "{\"build\":{\"artifact\":\"bookshelf\",\"name\":\"bookshelf\",\"time\":\"2022-12-03T23:31:16.598Z\",\"version\":\"0.0.1-SNAPSHOT\",\"group\":\"tinkoff.academy\"}}",
  "BookHunter": "BookHunter is not available"
}
```
