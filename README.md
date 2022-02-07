## Weather RESTful service

The service is built with Spring Boot 2.7 and Java 11. It has following design considerations:
- Multiple environment specific profiles for test,dev, and production.
- Has configurations broken down to dedicated properties and configuration class, easier to manage.
- Has clear Spring Boot application layers. Such as controller, service, repository, provider etc.
- Centralized exception handling.
- ApiKeyService to enforce the API Key scheme in thread safe way.
- Persist weather data into in-memory database H2 with JPA repository and thread safe.
- Use highly recommended [geocoding API](https://openweathermap.org/api/geocoding-api) instead of [built in geocoding](https://openweathermap.org/current#builtin) which is said to be decommissioned.

Some improvements in todo list:
- security on RESTful endpoints.
- Jacoco or Sonar codeCoverage matrix
- Swagger doc
- Hystrix circuit breaker
- In ApiKeyService, instead of ArrayList, better to use queue FIFO data structure to store the timestamps to achieve O(n) time complexity, its O(n<sup>2</sup>) atm

## How to start
Copy to local. In `weather` root folder, run `mvnw spring-boot:run` to start the application.

## H2 DB Access
Access http://localhost:8088/restapi/h2-console/login.jsp with the info according to application-{env}.yml
```
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:weatherprod
User Name: sa
Password: password
```

## The RESTful services
- http://localhost:8088/restapi/weather?country={country}&city={city}&apikey={apikey} HTTP Get # Load weather info for given country and city.
### apikey accepted as per application-{env}.yml
    - key1
    - key2
    - key3
    - key4
### country , city examples
    - AU, Melbourne
    - CN, Beijing
    - GB, London
## RESTful service examples

### Find weather of London UK
```
$ curl -s GET "http://localhost:8088/restapi/weather?country=GB&city=London&apikey=key2" | json_pp
{
   "description" : "broken clouds"
}
```
### Couldn't find weather with supplied City and Country
```
$ curl -s GET "http://localhost:8088/restapi/weather?country=AU&city=NotExistCity&apikey=key2" | json_pp
{
   "code" : 404,
   "message" : "The weather is not available given city = NotExistCity,country = AU"
}
```
### Invalid API Key
```
$ curl -s GET "http://localhost:8088/restapi/weather?country=GB&city=London&apikey=invalidKey" | json_pp
{
   "code" : 401,
   "message" : "supplied api key is not authorized!"
}
```
### Missing query parameters
```
$ curl -s GET "http://localhost:8088/restapi/weather" | json_pp
{
   "code" : 400,
   "message" : "city,country, and apikey are required query parameters. Example /restapi/weather?country={country}&city={city}&apikey={apikey}"
}
```
### When number of requests exceeds the maximum allowed per hour (5 as per application-{env}.yml).
```
$  curl -s GET "http://localhost:8088/restapi/weather?country=GB&city=London&apikey=key2" | json_pp
{
   "code" : 403,
   "message" : "Maximum access rate hour limit reached, try later!"
}
```
