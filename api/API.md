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

##### GET /ping
A simple ping endpoint which returns SC 200 for testing purposes.

#### POST /register
Used for user registration.
Consumes a credentials JSON object:
```json
{
  "name": "Wende",
  "password": "passwd"
}
```

#### POST /auth
Generates an authorization token for the given user credentials:
```json
{
  "name": "Wende",
  "password": "passwd"
}
```

And returns a JSON object with the generated token, if logged in successfully:
```json
{
  "token": "4c8cb23b-0655-4e15-96b2-8e1ee10376e6"
}
```
