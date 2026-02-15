package com.trevora.ecommerce.wishlist.repository;

import com.trevora.ecommerce.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    @EntityGraph(attributePaths = {
            "user",
            "items",
            "items.product",

    })
    Optional<Wishlist> findByUser_UserId(Long userId);
}
