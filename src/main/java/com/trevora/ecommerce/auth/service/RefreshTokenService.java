package com.trevora.ecommerce.auth.service;

import com.trevora.ecommerce.auth.entity.RefreshToken;
import com.trevora.ecommerce.auth.exception.ExpiredRefreshTokenException;
import com.trevora.ecommerce.auth.exception.InvalidRefreshTokenException;
import com.trevora.ecommerce.auth.repository.RefreshTokenRepository;
import com.trevora.ecommerce.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    @Transactional
    public RefreshToken create(User user) {
        refreshTokenRepository.deleteByUser(user);
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusMillis(refreshExpiration));

        return refreshTokenRepository.save(token);
    }

    public RefreshToken verify(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(InvalidRefreshTokenException::new);

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new ExpiredRefreshTokenException();
        }
        return refreshToken;
    }


    public void invalidate(String token) {
        refreshTokenRepository.findByToken(token)
                .ifPresent(refreshTokenRepository::delete);
    }
}

