package com.harsha.userlogin.service.auth;

import com.harsha.userlogin.domain.TokenBlackList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    @PersistenceContext
    private EntityManager entityManager;

    public void blacklistToken(String token) {
        TokenBlackList blacklistedToken = new TokenBlackList();
        blacklistedToken.setToken(token);
        blacklistedToken.setExpirationTime(Instant.now().plus(Duration.ofHours(24)));
        entityManager.persist(blacklistedToken);
    }

    @Transactional
    public boolean isTokenBlacklisted(String token) {
        TokenBlackList blacklistedToken = entityManager.find(TokenBlackList.class, token);
        return blacklistedToken != null && blacklistedToken.getExpirationTime().isAfter(Instant.now());
    }
}
