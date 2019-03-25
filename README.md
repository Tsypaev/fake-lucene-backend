# fake-lucene-backend
[![Build Status](https://travis-ci.org/Tsypaev/fake-lucene-backend.svg?branch=master)](https://travis-ci.org/Tsypaev/fake-lucene-backend)
## Methods:
* /ping - return OK if service is available;
* /search/q=< text > - return first 10 notes from DB which contains < text >.

## Docker Quickstart: 
`docker pull tsypaev/fake_lucene_backend`

`docker run tsypaev/fake_lucene_backend`
 
## Java Quickstart:
`mvn package` - create < jarfilename >.jar file;

`java -jar < jarfilename >.jar` - start application.
