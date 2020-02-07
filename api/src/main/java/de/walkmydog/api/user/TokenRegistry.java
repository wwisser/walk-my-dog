package de.walkmydog.api.user;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class TokenRegistry {
    
    private static final long TTL = TimeUnit.HOURS.toMillis(2);
    
    private final Map<UUID, Token> tokens = new ConcurrentHashMap<>();
    
    public UUID createToken(int userId) {
        UUID uuid = UUID.randomUUID();
        
        this.tokens.put(
            uuid,
            new Token(uuid, userId, System.currentTimeMillis() + TTL)
        );
        
        return uuid;
    }
    
    public boolean checkAndUpdate(UUID uuid) {
        Token token = this.tokens.get(uuid);
        
        if (token == null) {
            return false;
        }
        
        long now = System.currentTimeMillis();
        boolean valid = token.getValidUntil() >= now;
        
        if (valid) {
            token.setValidUntil(now + TTL);
        } else {
            this.tokens.remove(uuid);
        }
        
        return valid;
    }
    
    private static final class Token {
        private final UUID uuid;
        private final int userId;
        private long validUntil;

        public Token(UUID uuid, int userId, long validUntil) {
            this.uuid = uuid;
            this.userId = userId;
            this.validUntil = validUntil;
        }

        public UUID getUuid() {
            return this.uuid;
        }

        public int getUserId() {
            return this.userId;
        }

        public long getValidUntil() {
            return this.validUntil;
        }

        public void setValidUntil(long validUntil) {
            this.validUntil = validUntil;
        }
    }
    
}
