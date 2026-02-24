package com.trevora.ecommerce.Unit;

import com.trevora.ecommerce.auth.entity.RefreshToken;
import com.trevora.ecommerce.auth.exception.ExpiredRefreshTokenException;
import com.trevora.ecommerce.auth.exception.InvalidRefreshTokenException;
import com.trevora.ecommerce.auth.repository.RefreshTokenRepository;
import com.trevora.ecommerce.auth.service.RefreshTokenService;
import com.trevora.ecommerce.common.entity.User;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {
    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setUserId(1L);
    }

    @Test
    void create_shouldDeleteOldTokenAndSaveNew(){
        RefreshToken refreshToken = new RefreshToken();
        when(refreshTokenRepository.save(any()))
                .thenReturn(refreshToken);
        RefreshToken result = refreshTokenService.create(user);

        assertNotNull(result);

        verify(refreshTokenRepository).deleteByUser(user);
        verify(refreshTokenRepository).save(any(RefreshToken.class));

    }

    @Test
    void verify_validToken_shouldReturnToken(){
        RefreshToken token = new RefreshToken();
        token.setExpiryDate(Instant.now().plusSeconds(60));

        when(refreshTokenRepository.findByToken("valid"))
                .thenReturn(Optional.of(token));

        RefreshToken result = refreshTokenService.verify("valid");
        assertNotNull(result);
        verify(refreshTokenRepository, never()).delete(any());
    }

    @Test
    void verify_invalidToken_should_throwException(){
        when(refreshTokenRepository.findByToken("invalid"))
                .thenReturn(Optional.empty());

        assertThrows(InvalidRefreshTokenException.class,()->refreshTokenService.verify("invalid"));
    }

    @Test
    void verify_expiredToken_shouldDeleteAndThrow(){
        RefreshToken token = new RefreshToken();
        token.setExpiryDate(Instant.now().minusSeconds(10));

        when(refreshTokenRepository.findByToken("expired"))
                .thenReturn(Optional.of(token));
        assertThrows(ExpiredRefreshTokenException.class,()->refreshTokenService.verify("expired"));
        verify(refreshTokenRepository).delete(token);
    }

    @Test
    void invalidate_existingToken_shouldDelete(){
        RefreshToken token = new RefreshToken();
        when(refreshTokenRepository.findByToken("token"))
                .thenReturn(Optional.of(token));
        refreshTokenService.invalidate("token");
        verify(refreshTokenRepository).delete(token);
    }
}
