package de.walkmydog.api.user;

public interface UserStorage {

    User findById(final int id);

    User findByName(final String name);

    User saveUser(String name, String password);

}
