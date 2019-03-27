# fake-lucene-backend
[![Build Status](https://travis-ci.org/Tsypaev/fake-lucene-backend.svg?branch=master)](https://travis-ci.org/Tsypaev/fake-lucene-backend)
## Methods:
* /ping - return OK if service is available;
* /search/q=< text > - return first 10 notes from DB which contains < text >.

## Super Quickstart Backend and Frontend:
`docker-compose up`

## Docker Quickstart Backend: 
`docker pull tsypaev/fake_lucene_backend:0.0.2-SNAPSHOT`

`docker run -p < localport >:8080 tsypaev/fake_lucene_backend:0.0.2-SNAPSHOT`
 
## Docker Quickstart Frontend: 
`docker pull tsypaev/fake_lucene_frontend:0.0.2`

`docker run -p < localport >:8080 tsypaev/fake_lucene_frontend:0.0.2`
 
 
## Java Quickstart:
`mvn package` - create < jarfilename >.jar file;

`java -jar < jarfilename >.jar` - start application.
