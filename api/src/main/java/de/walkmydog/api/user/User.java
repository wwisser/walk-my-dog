package de.walkmydog.api.user;

public class User {

    private final int id;
    private final String name;

    private final transient String passwordHash;

    public User(int id, String name, String passwordHash) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + this.id +
            ", name='" + this.name + '\'' +
            '}';
    }

}
