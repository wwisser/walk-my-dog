package de.walkmydog.api.security;

import org.bson.types.ObjectId;

import java.util.UUID;

public class Token {

    private final UUID uuid;
    private final ObjectId userId;
    private long validUntil;

    public Token(UUID uuid, ObjectId userId, long validUntil) {
        this.uuid = uuid;
        this.userId = userId;
        this.validUntil = validUntil;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public ObjectId getUserId() {
        return this.userId;
    }

    public long getValidUntil() {
        return this.validUntil;
    }

    public void setValidUntil(long validUntil) {
        this.validUntil = validUntil;
    }

}
