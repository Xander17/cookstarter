

### Customer service API

**Регистрация нового пользователя**

POST /reg/customer
```json5
{
     "username": 'string', //обязательно,
     "pass1": 'string', //обязательно
     "pass2": 'string', //обязательно
     "firstName": 'string', //обязательно
     "lastName": 'string', //обязательно
     "email": 'string', //обязательно
     
 }
```
ответ
```json5
{
    "status": "OK",
}
```

**Регистрация нового ресторана**

POST /reg/restaurant
```json5
{
     "username": 'string', //обязательно,
     "password": 'string', //обязательно
     "role": 'string', //обязательно
 }
```
ответ
```json5
{
    "status": "OK",
}
```

**Аутентификация пользователя**

POST /auth
```json5
{
     "username": 'string', //обязательно,
     "password": 'string', //обязательно
 }
```
ответ
```json5
{
    "status": "OK",
    "token": "token with user details and role"
}
```
```json5
{
    "id": "1"//id пользователя или ресторана
}
```

### Restaurant service API
     
Любой запрос должен содержать header "Authentication" с Bearer + токеном, полученным при авторизации.

**Добавление карточки ресторана**

POST /restaurant/add
```json5
{
  "name": "first restaurant",//обязательно
  "description": "this is Russian restaurant",//обязательно
  "picture": 1 // restaurant picture id // не обязательно
}
```
ответ
```json5
{
  "id": 1,  
  "status": "OK"
}
```

**Обновление карточки ресторана**

POST /restaurant/update
```json5
{
     "id": 1,//обязательно
     "name": 'string', //обязательно
     "description": 'string', //обязательно
     "picture": 1 // restaurant picture id //обязательно
 }
```
ответ
```json5
{
    "status": "OK"
}
```

**Получение ресторана по id**

GET /restaurant/get/{id}

ответ
```json5
{
          
          "id": 1,
          "name": 'string', 
          "description": 'string', 
          "picture": 1 // restaurant picture id
              
 }
```

**Получение ресторана или списка по имени(его части)**

GET /restaurant/getByName/{name}

ответ
```json5
[
          {
          "id": 1,
          "name": 'string', 
          "description": 'string', 
          "picture": 1 // restaurant picture id
          },
          {
          "id": 2,
          "name": 'string', 
          "description": 'string', 
          "picture": 2 // restaurant picture id
          },
 ]
```

**Получение всех ресторанов**
GET /restaurant/getAll

ответ
```json5
[
          {
          "id": 1,
          "name": 'string', 
          "description": 'string', 
          "picture": 1 // restaurant picture id
          },
          {
          "id": 2,
          "name": 'string', 
          "description": 'string', 
          "picture": 2 // restaurant picture id
          },
 ]
```

**Удаление карточки ресторана**

GET /restaurant/delete/{id}

ответ
```json5
{
     "status": 'ok'
 }
```

**Добавление меню ресторана**

POST /menu/add

```json5
{
  "dishes": [
    {
    "name": "borsch",//обязательно
    "price": 0.99,//обязательно
    "description": "soup",//обязательно
    "pictureId": 124,
    "restaurantId": 1 //обязательно
  },
  {
    "name": "pel'meny",
    "price": 0.98,
    "description": "vkusnata",
    "pictureId": 123,
    "restaurantId": 1
  }
]
}
```
ответ
```json5
{
     "status": 'ok' 
          
 }
```

**Получение меню ресторана**

GET /menu/get{restaurantId}

ответ
```json5
[
          {
          "id": 1,
          "name": 'string',
          "price": 0.99, 
          "description": 'string', 
          "picture": 10 
           },
          {
          "id": 2,
          "name": 'string',
          "price": 0.99, 
          "description": 'string', 
          "picture": 11 // dish picture id
          }
]
```
```json5
{
     "status": 'ok' 
          
 }
```

**Добавление блюда**

POST /dish/add
```json5
{
 "name": "string",//обязательно
 "price": 0.99,//обязательно
 "description": "string",//обязательно
 "pictureId": 123,
  "restaurantId": 1 //обязательно
}
```
ответ
```json5
{
      "status": 'ok'     
 }
```

**Получеybt блюда**

POST /dish/get/{id}
ответ
```json5
{
 "id": 1,
 "name": "string",//обязательно
 "price": 0.99,//обязательно
 "description": "string",//обязательно
 "pictureId": 123,
  "restaurantId": 1 //обязательно
}
```
```json5
{
      "status": 'ok'     
 }
```

**Обновление блюда**

POST /dish/update
```json5
{
  "id": 1,
  "name": "string",//обязательно
  "price": 1000,//обязательно
  "description": "string",//обязательно
  "pictureId": 8,
  "restaurantId": 1 //обязательно
}
```
ответ
```json5
{
      "status": 'ok'     
 }
```

**Удаление блюда**

GET /dish/delete
```json5
{
  "id": 1
}
```
ответ
```json5
{
      "status": 'ok'     
 }
```

**Добавление контактов ресторана**

POST /contact/add
```json5
{
"address": "string",//обязательно
"phone": "string",//обязательно
"location": "string",//обязательно
"mail": "string",//обязательно
"website": "string",//обязательно
"restaurantId": 1 //обязательно
}
```
ответ
```json5
{
      "status": 'ok'     
 }
```

**Получение контактов ресторана**

GET /contact/get{restaurantId}

ответ
```json5
{
      "address": 'string',
      "phone": 'string',
      "location": 'string',
      "mail": 'string',
      "website": 'string',
      "restaurantId": 1
 }
```
```json5
{
      "status": 'ok'     
 }
```

**Обновление контактов ресторана**

POST /contact/update
```json5
{
  "address": "string",//обязательно
  "phone": "string",//обязательно
  "location": "string",//обязательно
  "mail": "string",//обязательно
  "website": "string",//обязательно
  "restaurantId": 1 //обязательно
}
```
ответ
```json5
{
      "status": 'ok'     
 }
```

**Удаление контактов ресторана**

GET /contact/delete{restaurantId}

ответ
```json5
{
      "status": 'ok'     
 }
```




### Picture service API

Любой запрос должен содержать header "Authentication" с Bearer + токеном, полученным при авторизации.


**Добавление картинки**

POST /picture/add

    ---файл---
ответ
```json5
{
    "status": "OK",
    "id": 1
}
```

**Обновление картнинки**

POST /picture/update/{id}

    ---файл---

ответ
```json5
{
    "status": "OK",
    "id": 1
}
```


**Получение картинки**

GET /picture/get/{id}/{token}

ответ

    ---файл---
```json5
{
     "status": 'ok', 
          
 }
```

**Удаление картинки**

GET /picture/delete/{id}

ответ
```json5
{
     "status": 'ok', 
          
 }
```


### Order service API

Любой запрос должен содержать header "Authentication" с Bearer + токеном, полученным при авторизации.

**Добавление нового заказа**

POST /orders/add
```json5
{
     "customerId": 42, //обязательно
     "restaurantId": 24, //обязательно
     "dishes": { // пустой список и нулевое количество не сохраняется
         "1": { // id блюда из меню
             "price": 0.99, //обязательно
             "quantity": 5 //обязательно
         },
         "2": {
             "price": 1.99,
             "quantity": 3
         }
     }
 }
```
ответ
```json5
{
    "status": "OK",
    "id": "23" //id созданного заказа
}
```
статусы ответов в конце страницы

---
**Обновление заказа**

POST /orders/update
```json5
{
    "id": 21, //обязательно
    "customerId": 42, //обязательно
    "restaurantId": 24, //обязательно
    "dishes": { // пустой список удаляет полностью заказ, блюда с нулевыми количествами удаляются
        "1": { // id блюда из меню
            "id": 62, //id блюда в заказе (не путать с меню), не обязательно
            "price": 0.99, //обязательно
            "quantity": 6 //обязательно
        },
        "2": {
            "id": 63,
            "price": 1.99,
            "quantity": 3
        }
    }
}
```
ответ
```json5
{
    "status": "OK",
}
```
---
**Удаление заказа**

GET /orders/delete/{id}

ответ
```json5
{
    "status": "OK",
}
```
---
**Удаление блюда из заказа**

GET /orders/delete/item/{id}

ответ
```json5
{
    "status": "OK",
}
```
---
**Получить информацию по заказу**

GET /orders/get/{id}

ответ
```json5
{
    "id": 21,
    "customerId": 42,
    "restaurantId": 24,
    "status": "SAVED",
    "dateCreated": "04-10-2020 13:11:41.800+0300",
    "dishes": {
        "1": {
            "id": 62,
            "price": 0.99,
            "quantity": 6
        },
        "2": {
            "id": 63,
            "price": 1.99,
            "quantity": 3
        }
    }
}
```
статусы заказов в конце страницы

---
**Получить все заказы по клиенту**

GET /orders/get/customer/{id}

ответ
```json5
[
    {
        "id": 22,
        "customerId": 42,
        "restaurantId": 24,
        "status": "SAVED",
        "dateCreated": "04-10-2020 13:23:29.614+0300",
        "dishes": {
            "1": {
                "id": 64,
                "price": 0.99,
                "quantity": 5
            },
            "2": {
                "id": 65,
                "price": 1.99,
                "quantity": 3
            }
        }
    },
    {
        "id": 23,
        "customerId": 42,
        "restaurantId": 24,
        "status": "SAVED",
        "dateCreated": "04-10-2020 13:27:20.316+0300",
        "dishes": {
            "1": {
                "id": 66,
                "price": 0.99,
                "quantity": 5
            },
            "2": {
                "id": 67,
                "price": 1.99,
                "quantity": 3
            }
        }
    }
]
```
---
**Получить все заказы по ресторану**

GET /orders/get/restaurant/{id}

ответ
```json5
[
    {
        "id": 22,
        "customerId": 42,
        "restaurantId": 24,
        "status": "SAVED",
        "dateCreated": "04-10-2020 13:23:29.614+0300",
        "dishes": {
            "1": {
                "id": 64,
                "price": 0.99,
                "quantity": 5
            },
            "2": {
                "id": 65,
                "price": 1.99,
                "quantity": 3
            }
        }
    },
    {
        "id": 23,
        "customerId": 42,
        "restaurantId": 24,
        "status": "SAVED",
        "dateCreated": "04-10-2020 13:27:20.316+0300",
        "dishes": {
            "1": {
                "id": 66,
                "price": 0.99,
                "quantity": 5
            },
            "2": {
                "id": 67,
                "price": 1.99,
                "quantity": 3
            }
        }
    }
]
```
---
**Ошибки**

*статус и сообщение, если ошибка известна*
```json5
{
    "status": "ERROR",
    "error": "Ошибка в запросе"
}
```
статусы ответов:
- OK
- ERROR (см. в конце страницы)

статусы заказов:
- SAVED - заказ сохранен
- PAID - оплачен
- IN_PROCESS - принят рестораном / готовится
- READY - готов к выдаче
- COMPLETED - завершен