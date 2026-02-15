package com.trevora.ecommerce.wishlist.repository;

import com.trevora.ecommerce.product.entity.Product;
import com.trevora.ecommerce.wishlist.entity.Wishlist;
import com.trevora.ecommerce.wishlist.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem,Long> {
    Optional<WishlistItem> findByProductAndWishlist(Product product, Wishlist wishlist);

    boolean existsByProductAndWishlist(Product product, Wishlist wishlist);
    List<WishlistItem> findAllByWishlist_User_UserId(Long wishlistUserUserId);
}
