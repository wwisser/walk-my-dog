package de.walkmydog.api;

import de.walkmydog.api.user.AuthRoute;
import de.walkmydog.api.user.LocalUserStorage;
import de.walkmydog.api.user.UserStorage;
import spark.Spark;

public class WalkMyDog {

    private static final int PORT = 8080;

    public static void main(String... args) {
        Spark.port(PORT);
        Spark.get("/ping", (request, response) -> {
            response.status(200);
            return "Health Check OK";
        });
        
        UserStorage userStorage = new LocalUserStorage();
        
        Spark.before((request, response) -> {
            
        });
        
        new AuthRoute(userStorage);
    }

}
