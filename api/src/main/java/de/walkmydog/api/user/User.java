package de.walkmydog.api.user;

import org.bson.types.ObjectId;

public class User {

    private ObjectId id;
    private String name;

    private String passwordHash;

    public User(ObjectId id, String name, String passwordHash) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public User() {
    }

    public ObjectId getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + this.id +
            ", name='" + this.name + '\'' +
            '}';
    }

}
