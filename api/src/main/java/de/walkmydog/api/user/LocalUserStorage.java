package de.walkmydog.api.user;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.mindrot.jbcrypt.BCrypt;

public class LocalUserStorage implements UserStorage {

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
