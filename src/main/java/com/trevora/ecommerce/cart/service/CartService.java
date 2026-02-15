package com.trevora.ecommerce.cart.service;

import com.trevora.ecommerce.cart.entity.Cart;
import com.trevora.ecommerce.cart.entity.CartItem;
import com.trevora.ecommerce.common.enums.CartStatus;
import com.trevora.ecommerce.product.entity.Product;
import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.cart.exception.CartItemNotFoundException;
import com.trevora.ecommerce.cart.exception.CartNotFoundException;
import com.trevora.ecommerce.product.exception.ProductNotFoundException;
import com.trevora.ecommerce.cart.repository.CartItemRepository;
import com.trevora.ecommerce.cart.repository.CartRepository;
import com.trevora.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    @Transactional
    public Cart addToCart( Long userId,Long productId,int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        Cart cart = cartRepository.findByUser_UserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseGet(()->createNewCart(userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        CartItem item = cartItemRepository.findByCartAndProduct(cart,product)
                .orElseGet(() -> {
                    CartItem ci = new CartItem();
                    ci.setCart(cart);
                    ci.setProduct(product);
                    ci.setQuantity(0);
                    cart.getCartItem().add(ci);
                    return ci;
                });
            item.setQuantity(item.getQuantity()+quantity);
        return cart;
    }
    private Cart createNewCart(Long userId){
        Cart cart = new Cart();
        cart.setStatus(CartStatus.ACTIVE);
        User user = new User();
        user.setUserId(userId);
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeFromCart(Long userId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByUser_UserIdAndStatus(userId,CartStatus.ACTIVE)
                .orElseThrow(CartNotFoundException::new);
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        CartItem item = cartItemRepository.findByCartAndProduct(cart,product)
                .orElseThrow(CartItemNotFoundException::new);
        if(quantity == null || quantity>= item.getQuantity()){
            cart.getCartItem().remove(item);
        }
        else{
            if(quantity<=0){
                throw new IllegalArgumentException("quantity must be greater than zero");
            }
            else{
                item.setQuantity(item.getQuantity()-quantity);
            }
        }
        return cart;
    }
}
