package com.trevora.ecommerce.service;

import com.trevora.ecommerce.entity.*;
import com.trevora.ecommerce.exception.ProductNotFoundException;
import com.trevora.ecommerce.exception.UserNotFoundException;
import com.trevora.ecommerce.exception.WishlistItemNotFoundException;
import com.trevora.ecommerce.repository.ProductRepository;
import com.trevora.ecommerce.repository.WishlistItemRepository;
import com.trevora.ecommerce.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;
    @Transactional
    public Wishlist addToWishlist(Long userId, Long productId) {
        Wishlist wishlist = wishlistRepository.findByUser_UserId(userId)
                .orElseGet(()->createNewWishlist(userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        boolean alreadyExists = wishlistItemRepository.existsByProductAndWishlist(product,wishlist);
        if(!alreadyExists){
            WishlistItem item = new WishlistItem();
            item.setWishlist(wishlist);
            item.setProduct(product);
            wishlist.getItems().add(item);
        }
       return wishlist;
    }

    public Wishlist createNewWishlist(Long userId){
        Wishlist wishlist = new Wishlist();
        User user = new User();
        user.setUserId(userId);
        wishlist.setUser(user);
        return wishlistRepository.save(wishlist);
    }

    @Transactional
    public Wishlist removeFromWishlist(Long userId, Long productId) {
        Wishlist wishlist = wishlistRepository.findByUser_UserId(userId)
                .orElseThrow(UserNotFoundException::new);
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        WishlistItem item = wishlistItemRepository.findByProductAndWishlist(product,wishlist)
                .orElseThrow(WishlistItemNotFoundException::new);
        wishlist.getItems().remove(item);
        return wishlist;
    }
}
