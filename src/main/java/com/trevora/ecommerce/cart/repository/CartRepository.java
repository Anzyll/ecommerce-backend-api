package com.trevora.ecommerce.cart.repository;

import com.trevora.ecommerce.cart.entity.Cart;
import com.trevora.ecommerce.common.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
  Optional<Cart> findByUser_UserIdAndStatus(Long userId, CartStatus status);
}
