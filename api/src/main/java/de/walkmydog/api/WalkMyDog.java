package de.walkmydog.api;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import de.walkmydog.api.security.AuthRoute;
import de.walkmydog.api.user.LocalUserStorage;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import spark.Spark;

import java.util.function.Consumer;

public class WalkMyDog {

    private static final int SPARK_PORT = 8080;
    private static final String MONGO_HOST = "78.47.142.22";

    public static void main(String... args) {
        Spark.port(SPARK_PORT);
        Spark.get("/ping", (request, response) -> {
            response.status(200);
            return "Health Check OK";
        });

        new AuthRoute(new LocalUserStorage());

        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClient.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(
                PojoCodecProvider
                    .builder()
                    .automatic(true)
                    .build()
            )
        );

        MongoClient mongoClient = new MongoClient(
            MONGO_HOST,
            MongoClientOptions
                .builder()
                .codecRegistry(pojoCodecRegistry)
                .build()
        );

        mongoClient.listDatabaseNames().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
    }

}
