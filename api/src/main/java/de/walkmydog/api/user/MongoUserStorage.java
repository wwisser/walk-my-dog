package de.walkmydog.api.user;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

public class MongoUserStorage implements UserStorage {

    private MongoCollection<User> collection;

    public MongoUserStorage(MongoDatabase mongoDatabase) {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClient.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(
                PojoCodecProvider
                    .builder()
                    .automatic(true)
                    .build()
            )
        );

        this.collection = mongoDatabase.withCodecRegistry(pojoCodecRegistry).getCollection("users", User.class);
    }

    @Override
    public User findById(int id) {
        return this.collection.find(Filters.eq("id", id)).first();
    }

    @Override
    public User findByName(String name) {
        return this.collection.find(Filters.eq("name", name)).first();
    }

    @Override
    public User saveUser(String name, String password) {
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(new ObjectId(), name, hash);

        this.collection.insertOne(user);

        return user;
    }

}
