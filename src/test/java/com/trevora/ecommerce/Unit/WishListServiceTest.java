package com.trevora.ecommerce.Unit;

import com.trevora.ecommerce.product.entity.Product;
import com.trevora.ecommerce.product.exception.ProductNotFoundException;
import com.trevora.ecommerce.product.repository.ProductRepository;
import com.trevora.ecommerce.wishlist.entity.Wishlist;
import com.trevora.ecommerce.wishlist.entity.WishlistItem;
import com.trevora.ecommerce.wishlist.exception.WishlistItemNotFoundException;
import com.trevora.ecommerce.wishlist.repository.WishlistItemRepository;
import com.trevora.ecommerce.wishlist.repository.WishlistRepository;
import com.trevora.ecommerce.wishlist.service.WishlistService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class WishListServiceTest {
    @Mock
    private WishlistRepository wishlistRepository;
    @Mock
    private WishlistItemRepository wishlistItemRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private WishlistService wishlistService;

    @Test
    void addToWishlist_validProduct_shouldAddItem() {
        Long userId = 1L;
        Long productId = 5L;

        Product product = new Product();
        product.setProductId(productId);
        when(wishlistRepository.findByUser_UserId(userId))
                .thenReturn(Optional.empty());
        when(wishlistRepository.save(any(Wishlist.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
        Wishlist result = wishlistService.addToWishlist(userId, productId);
        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        WishlistItem item = result.getItems().iterator().next();
        assertEquals(product, item.getProduct());
    }

    @Test
    void removeFromWishlist_existingItem_shouldRemoveItem(){
        Long userId = 1L;
        Long productId = 5L;
        Product product = new Product();
        product.setProductId(productId);

        WishlistItem item = new WishlistItem();
        item.setProduct(product);

        Wishlist wishlist = new Wishlist();
        wishlist.getItems().add(item);
        item.setWishlist(wishlist);

        when(wishlistRepository.findByUser_UserId(userId))
                .thenReturn(Optional.of(wishlist));
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
        when(wishlistItemRepository.findByProductAndWishlist(product,wishlist))
                .thenReturn(Optional.of(item));

        Wishlist result = wishlistService.removeFromWishlist(userId,productId);
        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
    }

    @Test
    void removeFromWishlist_itemNotFound_shouldThrowException() {
        Wishlist wishlist = new Wishlist();
        Product product = new Product();

        when(wishlistRepository.findByUser_UserId(1L))
                .thenReturn(Optional.of(wishlist));
        when(productRepository.findById(5L))
                .thenReturn(Optional.of(product));
        when(wishlistItemRepository.findByProductAndWishlist( product,wishlist))
                .thenReturn(Optional.empty());
        assertThrows(
                WishlistItemNotFoundException.class,
                () -> wishlistService.removeFromWishlist(1L, 5L)
        );
    }

    @Test
    void addToWishlist_productNotFound_shouldThrowException() {
        Wishlist wishlist = new Wishlist();

        when(wishlistRepository.findByUser_UserId(1L))
                .thenReturn(Optional.of(wishlist));
        when(productRepository.findById(5L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> wishlistService.addToWishlist(1L, 5L)
        );
    }

}
