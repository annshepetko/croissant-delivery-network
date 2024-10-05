
This application is written on Spring Boot and using microservice 
architecture and provides API to interact with croissant delivery network


Key features for user:
-

- JWT authentication based on access/refresh tokens
- Reset password feature (If user forgot his password he can send request to restore password via email notification)
- User profile and discount system(If user is registered after each order he get a little discount )
- Email notification after each order what contains info about order
- Admin panel for orders and products which allow admins see all info about products and orders. Also admin panel allows to modify and add new products to menu

Stack: 
-

Java 17, Spring Boot, Spring Security,
Spring Data Jpa,
Spring cloud,
Junit, Mockito,
Flyway,
Apache Kafka
Api gateway,
JWT,
Eureka server,
Docker / docker-compose


Key features for developers: 

- Async communication among microservices what using Apache kafka
- Role segregation (Admin, User)
- Pre-filters in Api gateway, if user is sending request to admin endpoints this request handling in api gateway, and api gateway "asking" in user-service is user admin or no after this api gateway decides allow request or no
- Unit testing 
- Exception handling
- In order microservice are two order processors depends on user is registered or no, if yes to user and notification microservices sending a notifications for user profile and email notification respectively
- Template pattern in notification microservice, what allows to send different mails depends on topic
- SOLID principles and patterns
- Swagger for documentation API

For interact with api use swagger 
-
for example:

- user microservice will run on: http://localhost:8080 , to see API documentation you should send request to http://localhost:8080/swagger-ui/index.html
- product microservice will run on port 8050, so send request to http://localhost:8050/swagger-ui/index.html for product API documentation
- order microservice will run on http://localhost:8060, so send request to http://localhost:8060/swagger-ui/index.html

But
-

- After you see all API documentation for services, you should send requests to http://localhost:9000  (This is api gateway)   



HOW I CAN RUN IT ?

 - You must have DOCKER on your machine
 - After you have pulled repo you should open terminal and move to current project directory
 - After this you should run : docker compose up


---
Thanks for fork :D !)