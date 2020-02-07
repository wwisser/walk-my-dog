package de.walkmydog.api.security;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.walkmydog.api.user.User;
import de.walkmydog.api.user.UserStorage;
import org.eclipse.jetty.http.HttpStatus;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;
import spark.Spark;

public class AuthRoute {

    private static final Set<String> EXCLUDED_ROUTES = Stream.of(
        "/auth",
        "/register",
        "/ping"
    ).collect(Collectors.toSet());

    private final UserStorage userStorage;
    private final Gson gson = new Gson();
    private final TokenRegistry tokenRegistry = new TokenRegistry();

    public AuthRoute(UserStorage userStorage) {
        this.userStorage = userStorage;

        Spark.post("/auth", this::handleAuthorization);
        Spark.post("/register", this::handleRegistration);

        Spark.before((request, response) -> {
            if (EXCLUDED_ROUTES.contains(request.pathInfo())) {
                return;
            }

            String authHeader = request.headers("Authorization");

            if (authHeader == null || authHeader.trim().isEmpty()) {
                Spark.halt(HttpStatus.UNAUTHORIZED_401, "Authorization Header not given.");
                return;
            }

            UUID token = UUID.fromString(authHeader);
            Optional<Token> tokenOpt = this.tokenRegistry.checkAndUpdate(token);

            if (!tokenOpt.isPresent()) {
                Spark.halt(HttpStatus.UNAUTHORIZED_401, "Authorization token invalid.");
            }
        });
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

        if (this.isNullOrEmpty(credentials.name) || this.isNullOrEmpty(credentials.password)) {
            Spark.halt(HttpStatus.BAD_REQUEST_400, "Invalid user credentials");
            return null;
        }

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

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}
