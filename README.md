# BikeSpringbootApiRest
An example api rest created with springboot

Api queries:

Get All:
GET> http://localhost:8080/api/bikes

Create new:
POST> http://localhost:8080/api/bikes
(a raw json body)
{
    "name": "Trek Rampage",
    "size": "XL",
    "type": "MTB"
}

Update entry by id:
PUT> http://localhost:8080/api/bikes
(a raw json body)
{
    "id": 4,
    "name": "Trek crossroad",
    "size": "M",
    "type": "ROAD"
}

Delete entry by id:
DELETE> 

