package com.trevora.ecommerce.repository;

import com.trevora.ecommerce.entity.Product;
import com.trevora.ecommerce.entity.Wishlist;
import com.trevora.ecommerce.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem,Long> {
    Optional<WishlistItem> findByProductAndWishlist(Product product, Wishlist wishlist);

    boolean existsByProductAndWishlist(Product product, Wishlist wishlist);
}
