# BlackBooks 1 шаг
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
1. То, что по идее должно быть @Beans оставил в конструкторах  
2. Т.к. работа с другими сервисами пока не входит в задание, то в местах работы с сущностями других сервисов были реализованы не очень правильные заглушки, которые тем не менее позволят протестировать данный сервис.
