package de.walkmydog.api;

import spark.Spark;

public class WalkMyDog {

    private static final int PORT = 8080;

    public static void main(String... args) {
        Spark.port(PORT);
        Spark.get("/ping", (request, response) -> {
            response.status(200);
            return "Health Check OK";
        });
    }

}
