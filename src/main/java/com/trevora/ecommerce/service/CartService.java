package com.trevora.ecommerce.service;

import com.trevora.ecommerce.entity.Cart;
import com.trevora.ecommerce.entity.CartItem;
import com.trevora.ecommerce.entity.Product;
import com.trevora.ecommerce.entity.User;
import com.trevora.ecommerce.exception.ProductNotFoundException;
import com.trevora.ecommerce.repository.CartItemRepository;
import com.trevora.ecommerce.repository.CartRepository;
import com.trevora.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    public Cart addToCart( Long userId,Long productId,int quantity) {
        Cart cart = cartRepository.findByUser_UserIdAndStatus(userId,"ACTIVE")
                .orElseGet(()->createNewCart(userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        CartItem item = cartItemRepository.findByCartAndProduct(cart,product)
                .orElse(null);
        if(item==null){
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.getCartItem().add(item);
        }
        else {
            item.setQuantity(item.getQuantity()+quantity);
        }
        return cartRepository.save(cart);
    }
    private Cart createNewCart(Long userId){
        Cart cart = new Cart();
        cart.setStatus("ACTIVE");
        User user = new User();
        user.setUserId(userId);
        cart.setUser(user);
        return cartRepository.save(cart);
    }
}
