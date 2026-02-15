package com.trevora.ecommerce.auth.repository;

import com.trevora.ecommerce.auth.entity.RefreshToken;
import com.trevora.ecommerce.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}

