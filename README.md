[![Build Status](https://travis-ci.org/Tsypaev/fake-lucene-backend.svg?branch=master)](https://travis-ci.org/Tsypaev/fake-lucene-backend)

# fake-lucene-backend
## Technology:
* java 1.8;
* node.js;
* spring-boot;
* postgreSql.

## Methods:
* /ping - return OK if service is available;
- /lucene/search/q=< text >, type= < type > - return first 10 notes from DB;
  - q=text - serching text;
  - type=type - type of searching("all" - search by name of movie, othr value - serach by year and movie).
- /search/q=< text >, type= < type > - return first 10 notes from DB;
  - q=text - serching text;
  - type=type - type of searching("all" - search by name of movie, "year" - serach by year and movie).

## Sources:
* backend - https://github.com/Tsypaev/fake-lucene-backend 
* frontend - https://github.com/vysokiylev/lab1-front

## Authors:
* backend - https://github.com/Tsypaev
* frontend - https://github.com/vysokiylev

## Super Quickstart Backend and Frontend:
`docker-compose up` - app will be started on localhost:3000

## Docker Quickstart Backend: 
`docker pull tsypaev/fake_lucene_backend:0.0.2-SNAPSHOT`

`docker run -p < localport >:8080 tsypaev/fake_lucene_backend:0.0.2-SNAPSHOT`
 
## Docker Quickstart Frontend: 
`docker pull tsypaev/fake_lucene_frontend:0.0.2`

`docker run -p < localport >:3000 tsypaev/fake_lucene_frontend:0.0.2`
 
 
## Java Quickstart:
`mvn package` - create < jarfilename >.jar file;

`java -jar < jarfilename >.jar` - start application.

## Node.js Quickstart:
requirements: node.js 7+;

`npm install` - install dependencies;

`npm run build` - build web;

`npm run start` - start application.




