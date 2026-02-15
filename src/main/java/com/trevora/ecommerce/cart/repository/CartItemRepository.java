package com.trevora.ecommerce.cart.repository;

import com.trevora.ecommerce.cart.entity.Cart;
import com.trevora.ecommerce.cart.entity.CartItem;
import com.trevora.ecommerce.common.enums.CartStatus;
import com.trevora.ecommerce.product.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart , Product product);
   @EntityGraph(attributePaths = "product")
    List<CartItem> findAllByCart_User_UserIdAndCart_Status(Long cartUserUserId, CartStatus cartStatus);
}
