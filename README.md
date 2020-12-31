# BikeSpringbootApiRest
An example api rest created with springboot and SQLite portable database, you only need to download, get dependencies and start the proyect
* you can uncomment application.properties lines for set MySQL database too


Api queries:

Get All:
GET> http://localhost:8080/api/bikes


Get By ID:
GET> http://localhost:8080/api/bikes/2

Create new:
POST> http://localhost:8080/api/bikes
(a raw json body)
{
    "name": "Trek Rampage",
    "size": "XL",
    "type": "MTB",
    "mailBrand": "brandbike@mail.com"
}

Update entry by id:
PUT> http://localhost:8080/api/bikes
(a raw json body)
{
    "id": 4,
    "name": "Trek crossroad",
    "size": "M",
    "type": "ROAD",
    "mailBrand": "brandbike@mail.com"
}

Delete entry by id:
DELETE> http://localhost:8080/api/bikes/5

