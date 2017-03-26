# youtube-api

Google youtube-api test with Spring boot application.
Add your YouTube API key to properties before compiling the code.
Java 1.8 and Maven 3.3.x required.

## Build with tests

```
mvn clean install  
```

## Build, skip tests

```
mvn clean install -DskipTests=true
```

## Run Spring boot application

```
java -jar target/youtube-api-1.0-SNAPSHOT.jar
```

## Test search with curl

```
curl localhost:8080/google-youtube-api?search="Meshuggah"
```
