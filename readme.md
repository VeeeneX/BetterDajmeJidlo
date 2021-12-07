# BetterDajmeJidlo



For anyone who is hungry enough BetterDajmeJidlo provides fast and simple way to order your favorite food. Just choose any restaurant you like and thanks to our modern infrastructure you will be notified during whole process of delivering your dream food.  
  
This project was created as a part of the course PV217 - Service oriented architecture. It demonstrates usage of a microservice architecture concept and their advantage in selected abilities. In addition, well known services can be found and are used by this project like Redis serving as a eventbus, Graphana providing data monitoring and visualisation, Postgresql and Mongodb operating within data layer while Minio being used as S3 storage. Project consist of these services:  

1. Order Service
2. Restaurant Service
3. Delivery Managment Service
4. Payments Service
5. Billing Service  
6. Notification Service
<br></br>
**Simplified project diagram:**  
  
![](./docs/diagram.png)

## Build

```
docker network create better-dajme-jidlo
docker-compose -f docker/swarmpit.yml up -d

# Database
docker-compose -f docker/database.yml up -d

# Tasks
docker-compose -f docker/tasks.yml up -d

# Application
docker-compose -f docker/application.yml up -d
```

## Running

```shell
docker-compose up
```


## Ports & Hostnames

```
127.0.20.0 databases.local
127.0.20.1 user.service.local
127.0.20.2 restaurant.feed.local
127.0.20.3 restaurant.service.local
127.0.20.4 payment.service.local
127.0.20.5 notification.service.local
127.0.20.6 billing.service.local
127.0.20.7 order.service.local
```
