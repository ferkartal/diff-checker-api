# Task Assignment 

[![N|Solid](https://s3-sa-east-1.amazonaws.com/thedevconf/2017/img/logo/diamond/diamond-waes.png)](https://www.wearewaes.com)

## Diff- Checker API
According to the task, api call can be using for the checking Json data and make comparison between them.

  - Endpoints that accept JSON base64 encoded binary data
  - The provided data needs to be diff-ed and the results are available on a third end point
  - The results provide the following info in JSON format
    -- If equal
    -- If not of equal size just 
    -- If of same size provide insight in where the diffs are, actual diffs are not needed.
    > Mainly offsets + length in the data

## Tech

Assignment includes following technologies:

* [Java] - Java 11
* [Spring Framework] - is an application framework and inversion of control container for the Java platform.
* [Lombok] - is a java library that automatically plugs into your editor and build tools
* [Caffeine] - high performance caching
* [h2] - database Engine

## Project Structure
The directory structure of the project looks like the following.
```sh
\---src
|   +---main
|   |   \---waes
|   |   |   \---controller
|   |   |   \---domain
|   |   |   |   \---entity
|   |   |   |   \---response
|   |   |   \---exception
|   |   |   \---repository
|   |   |   \---service
|   |   \---resources
|   |   |   |---application.properties
|   |   |   |---schema.sql
|   +---test
|       \---waes
|       |   |---controller
|       |   |   |---
|       |   |---service
|       \---resources
|       |   |---application.properties
|---pom.xml
```
### Pre-requisite
Before running the project on your local machine, please be sure that following items should be installed;
- Docker

### Installation

Clone the source code.

```sh
$ git clone <URL>
```
#### Running Tests

```sh
$ mvn test
```
### Deploy the project

####  Docker - build the images

- Go to the "diff-checker-api" folder
```
$ cd diff-checker-api
$ docker build -t diff-checker-api .
```

#### Docker - run the containers
```
docker run -p 8080:8080 diff-checker-api 
```

### Sample Requests

#### Example #1
```sh
POST http://localhost:8080/v1/diff/1/left
```
##### Encoded Base64 Data Request #1
- eyJkYXRhIjoibW9uZGF5In0=
##### Decoded Version of Request #1
- {"data":"monday"}
```
POST http://localhost:8080/v1/diff/1/right
```
##### Encoded Base64 Data Request #2
-  eyJkYXRlIjoic3VuZGF5In0= 
##### Decoded Version of Request #2
- {"date":"sunday"}

#### Result of diff-check api

```
GET http://localhost:8080/v1/diff/1
{
    "diffType": "NotEqual",
    "diffs": [
        {
            "offset": 5,
            "length": 1
        },
        {
            "offset": 9,
            "length": 2
        }
    ]
}
```
#### Example #2
##### Encoded Base64 Data Request #1
- eyJkYXRlIjoic3VuZGF5In0=
##### Decoded Version of Request #1
- {"date":"sunday"}
```
POST http://localhost:8080/v1/diff/1/left
```
##### Encoded Base64 Data Request #2
- eyJkYXRlIjoic3VuZGF5In0=
##### Decoded Version of Request #2
- {"date":"sunday"}
```
POST http://localhost:8080/v1/diff/1/right
```
#### Result of diff-check api
```
GET http://localhost:8080/v1/diff/1

{
    "diffType": "Equal"
}
```
#### Example #3
##### Encoded Base64 Data Request #1
- eyJkYXRlIjoic3VuZGF5In0= 
##### Decoded Version of Request #1
- {"date":"sunday"}
```
POST http://localhost:8080/v1/diff/1/left
```
##### Encoded Base64 Data Request #2
- eyJkYXRhIjoidHVlc2RheSJ9
##### Decoded Version of Request #2
- {"date":"tuesday"}
```
POST http://localhost:8080/v1/diff/1/right
```
#### Result of diff-check api
```
GET http://localhost:8080/v1/diff/1

{
    "diffType": "DifferentSize"
}
```

Author
----
Nur Erkartal


   [Java]: <https://www.java.com>
   [caffeine]: <https://github.com/ben-manes/caffeine>
   [Spring Framework]: <http://spring.io>
   [lombok]: <https://projectlombok.org/>
   [h2]: <https://www.h2database.com/html/main.html>
   [jackson-core]: <https://github.com/FasterXML/jackson-core>



   
  

