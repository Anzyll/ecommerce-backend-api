package com.trevora.ecommerce.unit;

import com.trevora.ecommerce.cart.entity.Cart;
import com.trevora.ecommerce.cart.entity.CartItem;
import com.trevora.ecommerce.cart.exception.CartItemNotFoundException;
import com.trevora.ecommerce.cart.exception.CartNotFoundException;
import com.trevora.ecommerce.cart.repository.CartItemRepository;
import com.trevora.ecommerce.cart.repository.CartRepository;
import com.trevora.ecommerce.cart.service.CartService;
import com.trevora.ecommerce.common.enums.CartStatus;
import com.trevora.ecommerce.product.entity.Product;
import com.trevora.ecommerce.product.exception.ProductNotFoundException;
import com.trevora.ecommerce.product.repository.ProductRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private CartService cartService;

    @Test
    void addItem_newProduct_shouldCreateCartItem(){
        Long userId = 1L;
        Long productId = 10L;
        int quantity = 2;

        Cart cart = new Cart();
        cart.setStatus(CartStatus.ACTIVE);
        cart.setCartItem(new ArrayList<>());

        Product product = new Product();
        product.setProductId(productId);

        when(cartRepository.findByUser_UserIdAndStatus(userId, CartStatus.ACTIVE))
                .thenReturn(Optional.of(cart));

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        when(cartItemRepository.findByCartAndProduct(cart, product))
                .thenReturn(Optional.empty());

        Cart result = cartService.addToCart(userId,productId,quantity);

        assertNotNull(result);
        assertEquals(1,result.getCartItem().size());
        CartItem item = result.getCartItem().get(0);
        assertEquals(product, item.getProduct());
        assertEquals(quantity, item.getQuantity());
        assertEquals(cart, item.getCart());

    }

    @Test
    void updateItemQuantity_shouldIncreaseQuantity(){
        Long userId = 1L;
        Long itemId = 5L;
        int delta = 1;

        Cart cart = new Cart();
        cart.setStatus(CartStatus.ACTIVE);

        CartItem item = new CartItem();
        item.setQuantity(2);

        when(cartRepository.findByUser_UserIdAndStatus(userId,CartStatus.ACTIVE))
                .thenReturn(Optional.of(cart));

        when(cartItemRepository.findByIdAndCart(itemId,cart))
                .thenReturn(Optional.of(item));

        Cart result = cartService.updateCartItemQuantity(userId,itemId,delta);
        assertNotNull(result);
        assertEquals(3,item.getQuantity());
    }

    @Test
     void removeFromCart_existingItem_shouldDeleteItem(){
        Long userId = 1L;
        Long productId = 5L;

        Cart cart = new Cart();
        cart.setStatus(CartStatus.ACTIVE);

        Product product = new Product();
        product.setProductId(productId);

        CartItem item = new CartItem();

        when(cartRepository.findByUser_UserIdAndStatus(userId,CartStatus.ACTIVE))
                .thenReturn(Optional.of(cart));
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
        when(cartItemRepository.findByCartAndProduct(cart,product))
                .thenReturn(Optional.of(item));

        Cart result = cartService.removeFromCart(userId,productId);

        assertNotNull(result);
        verify(cartItemRepository).delete(item);
    }

    @Test
    void removeFromCart_cartNotFound_shouldThrowException() {
        Long userId = 1L;
        Long productId = 5L;

        when(cartRepository.findByUser_UserIdAndStatus(userId, CartStatus.ACTIVE))
                .thenReturn(Optional.empty());

        assertThrows(
                CartNotFoundException.class,
                () -> cartService.removeFromCart(userId, productId)
        );

        verify(cartRepository).findByUser_UserIdAndStatus(userId, CartStatus.ACTIVE);
        verifyNoInteractions(productRepository, cartItemRepository);
    }

    @Test
    void addToCart_productNotFound_shouldThrowException(){
        Long userId = 1L;
        Long productId = 5L;
        int quantity=3;

        Cart cart = new Cart();
        cart.setStatus(CartStatus.ACTIVE);

        when(cartRepository.findByUser_UserIdAndStatus(userId, CartStatus.ACTIVE))
                .thenReturn(Optional.of(cart));
        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,()->cartService.addToCart(userId,productId,quantity));
        verifyNoInteractions( cartItemRepository);
    }

    @Test
    void removeFromCart_cartItemNotFound_shouldThrowException() {
        Long userId = 1L;
        Long productId = 5L;

        Cart cart = new Cart();
        cart.setStatus(CartStatus.ACTIVE);
        Product product = new Product();
        product.setProductId(productId);
        when(cartRepository.findByUser_UserIdAndStatus(userId, CartStatus.ACTIVE))
                .thenReturn(Optional.of(cart));
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
        when(cartItemRepository.findByCartAndProduct(cart, product))
                .thenReturn(Optional.empty());
        assertThrows(
                CartItemNotFoundException.class,
                () -> cartService.removeFromCart(userId, productId)
        );
        verify(cartItemRepository).findByCartAndProduct(cart, product);
        verify(cartItemRepository, never()).delete(any());
    }

    @Test
    void addToCart_invalidQuantity_shouldThrowException() {
        Long userId = 1L;
        Long productId = 5L;

        assertThrows(
                IllegalArgumentException.class,
                () -> cartService.addToCart(userId, productId, 0)
        );
        verifyNoInteractions(cartRepository, productRepository, cartItemRepository);
    }

}
