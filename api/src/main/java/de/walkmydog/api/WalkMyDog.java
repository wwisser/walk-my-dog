package de.walkmydog.api;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import de.walkmydog.api.security.AuthRoute;
import de.walkmydog.api.user.LocalUserStorage;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import spark.Spark;

public class WalkMyDog {

    private static final int PORT = 8080;
    private static final String MONGO_HOST = "localhost";

    public static void main(String... args) {
        Spark.port(PORT);
        Spark.get("/ping", (request, response) -> {
            response.status(200);
            return "Health Check OK";
        });
        
        new AuthRoute(new LocalUserStorage());
        
        CodecRegistry pojoCodecRegistry = CodecRegistries
                .fromRegistries(
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
    }

}
