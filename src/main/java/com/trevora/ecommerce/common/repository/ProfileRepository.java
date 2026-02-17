package com.trevora.ecommerce.common.repository;

import com.trevora.ecommerce.common.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {
    Optional<Profile> findByUser_UserId(Long userUserId);
}
