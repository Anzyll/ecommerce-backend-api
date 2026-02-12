package com.trevora.ecommerce.repository;

import com.trevora.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
  Optional<Cart> findByUser_UserIdAndStatus(Long userId, String status);
}
