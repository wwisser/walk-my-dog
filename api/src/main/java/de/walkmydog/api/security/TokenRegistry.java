package de.walkmydog.api.security;

import java.util.Map;
import java.util.Optional;
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
    
    public Optional<Token> checkAndUpdate(UUID uuid) {
        Token token = this.tokens.get(uuid);
        
        if (token == null) {
            return Optional.empty();
        }
        
        long now = System.currentTimeMillis();
        boolean valid = token.getValidUntil() >= now;
        
        if (valid) {
            token.setValidUntil(now + TTL);
            return Optional.of(token);
        } else {
            this.tokens.remove(uuid);
            return Optional.empty();
        }
    }
    
}
