package de.walkmydog.api;

import spark.Spark;

public class WalkMyDog {

    public static void main(String... args) {
        Spark.port(8080);
        Spark.get("/ping", (request, response) -> 1);
    }

}
