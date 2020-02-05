package de.walkmydog.api.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.UUID;
import org.eclipse.jetty.http.HttpStatus;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;
import spark.Spark;

public class AuthRoute {

    private final UserStorage userStorage;
    private final Gson gson = new Gson();
    private final TokenRegistry tokenRegistry = new TokenRegistry();

    public AuthRoute(UserStorage userStorage) {
        this.userStorage = userStorage;

        Spark.post("/auth", this::handleAuthorization);
        Spark.post("/register", this::handleRegistration);
    }

    private Object handleAuthorization(Request request, Response response) throws Exception {
        Credentials credentials = this.gson.fromJson(request.body(), Credentials.class);
        
        User user = this.userStorage.findByName(credentials.name);
        
        if (null == user) {
            Spark.halt(HttpStatus.UNAUTHORIZED_401, "User name does not exist.");
            return null;
        }
        
        boolean validPasswd = BCrypt.checkpw(
            credentials.password,
            user.getPasswordHash()
        );
        
        if (!validPasswd) {
            Spark.halt(HttpStatus.UNAUTHORIZED_401, "Given password is wrong.");
            return null;
        }
        
        UUID uuid = this.tokenRegistry.createToken(user.getId());
        
        JsonObject tokenObject = new JsonObject();
        tokenObject.addProperty("token", uuid.toString());
        
        return this.gson.toJson(tokenObject);
    }

    private Object handleRegistration(Request request, Response response) throws Exception {
        Credentials credentials = this.gson.fromJson(request.body(), Credentials.class);

        if (this.userStorage.findByName(credentials.name) != null) {
            Spark.halt(HttpStatus.CONFLICT_409, "User name already exists.");
        }

        // TODO validate password strength
        User createdUser = this.userStorage.saveUser(credentials.name, credentials.password);
        response.status(HttpStatus.CREATED_201);

        return this.gson.toJson(createdUser);
    }

    private static class Credentials {
        private String name;
        private String password;
    }

}
