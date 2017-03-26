# youtube-api

Google YouTube API test with simple Spring boot application with REST API.
Add your YouTube API key to properties before compiling the code,
or set environment variable YOUTUBE_APIKEY.
Java 1.8 and Maven 3.3.x required.


## Set your API key; Linux, MacOS

```
export YOUTUBE_APIKEY=foobar
```

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
