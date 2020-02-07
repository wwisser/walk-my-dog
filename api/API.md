# API
REST interface to perform CRUD operations for database entities.

## Build & run jar file
```
cd api
mvn clean package
java -jar target/api-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Check if your application is running:
```
curl http://localhost:8080/ping
```

## Routes

##### /ping
A simple ping endpoint which returns SC 200 for testing purposes.

#### /register
Used for user registration.
Consumes a credentials JSON object:
```json
{
  "name": "Wende",
  "password": "passwd"
}
```
