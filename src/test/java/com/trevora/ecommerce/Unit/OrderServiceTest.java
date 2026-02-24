package com.trevora.ecommerce.Unit;

import com.trevora.ecommerce.cart.entity.Cart;
import com.trevora.ecommerce.cart.entity.CartItem;
import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.common.enums.OrderStatus;
import com.trevora.ecommerce.order.entity.Order;
import com.trevora.ecommerce.order.repository.OrderRepository;
import com.trevora.ecommerce.order.service.OrderService;
import static org.junit.jupiter.api.Assertions.*;

import com.trevora.ecommerce.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderService orderService;
    private  Cart cart;
    private User user;

    @BeforeEach
    void setup(){
         cart = new Cart();
         user = new User();
        user.setUserId(1L);
        cart.setUser(user);
        cart.setCartItem(new ArrayList<>());

        Product product = new Product();
        product.setPrice(new BigDecimal("100"));
        product.setStock(10);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        cart.setCartItem(List.of(cartItem));
    }

    @Test
    void createOrder_validCart_shouldCreateOrder(){

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

         Order order = orderService.createOrder(cart, "Kerala, India");
        assertNotNull(order);
        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertEquals(user, order.getUser());
        assertEquals("Kerala, India", order.getShippingAddress());

         verify(orderRepository).save(any(Order.class));
    }

    @Test
    void createOrder_shouldCalculateTotalAmount(){
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation-> invocation.getArgument(0));
    Order order = orderService.createOrder(cart,"Kerala");
    assertEquals(BigDecimal.valueOf(200),order.getTotalAmount());
    }

    @Test
    void createOrder_shouldReduceProductStock() {
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        orderService.createOrder(cart, "Kerala");

        Product product = cart.getCartItem().get(0).getProduct();
        assertEquals(8, product.getStock());
    }


}
