package de.walkmydog.api;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import de.walkmydog.api.security.AuthRoute;
import de.walkmydog.api.user.MongoUserStorage;
import spark.Spark;

public class WalkMyDog {

    private static final int SPARK_PORT = 8080;
    private static final String MONGO_HOST = "78.47.142.22";

    public static void main(String... args) {
        Spark.port(SPARK_PORT);
        Spark.get("/ping", (request, response) -> {
            response.status(200);
            return "Health Check OK";
        });

        MongoDatabase adminDb = new MongoClient(MONGO_HOST)
            .getDatabase("admin");

        new AuthRoute(new MongoUserStorage(adminDb));
    }

}
