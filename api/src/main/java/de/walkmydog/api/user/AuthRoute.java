package de.walkmydog.api.user;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AuthRoute {

    private final UserStorage userStorage;
    private final Gson gson = new Gson();

    public AuthRoute() {
        this.userStorage = new LocalUserStorage();

        Spark.post("/auth", this::handleAuthorization);
        Spark.post("/register", this::handleRegistration);
    }

    private Object handleAuthorization(Request request, Response response) throws Exception {
        return null;
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

    /**
     * Dummy storage implementation due to testing.
     */
    private static class LocalUserStorage implements UserStorage {

        private final List<User> users = new CopyOnWriteArrayList<>();

        @Override
        public User findById(int id) {
            return this.users
                .stream()
                .filter(user -> user.getId() == id)
                .findAny()
                .orElse(null);
        }

        @Override
        public User findByName(String name) {
            return this.users
                .stream()
                .filter(user -> name.equals(user.getName()))
                .findAny()
                .orElse(null);
        }

        @Override
        public User saveUser(String name, String password) {
            int id = this.users.stream().mapToInt(User::getId).max().orElse(0) + 1;

            String hash = BCrypt.hashpw(password, BCrypt.gensalt());

            User user = new User(id, name, hash);
            this.users.add(user);

            return user;
        }

    }

}
