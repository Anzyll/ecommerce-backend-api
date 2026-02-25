package com.trevora.ecommerce.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trevora.ecommerce.cart.entity.Cart;
import com.trevora.ecommerce.cart.entity.CartItem;
import com.trevora.ecommerce.cart.repository.CartItemRepository;
import com.trevora.ecommerce.cart.repository.CartRepository;
import com.trevora.ecommerce.common.entity.Role;
import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.common.enums.CartStatus;
import com.trevora.ecommerce.common.repository.RoleRepository;
import com.trevora.ecommerce.common.repository.UserRepository;
import com.trevora.ecommerce.order.dto.CheckoutRequestDto;
import com.trevora.ecommerce.order.entity.Order;
import com.trevora.ecommerce.order.repository.OrderRepository;
import com.trevora.ecommerce.product.entity.Activity;
import com.trevora.ecommerce.product.entity.Category;
import com.trevora.ecommerce.product.entity.Product;
import com.trevora.ecommerce.product.repository.ActivityRepository;
import com.trevora.ecommerce.product.repository.CategoryRepository;
import com.trevora.ecommerce.product.repository.ProductRepository;
import com.trevora.ecommerce.security.CustomUserDetails;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderPlacementIntegrationTest extends BaseIntegrationTest{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private RoleRepository roleRepository;
    Product product;
    Category category;
    Activity activity;
    Role role;
    User testUser;
    Cart cart;
    CartItem cartItem;

    @BeforeEach
    void setup() {
        category = categoryRepository.findByName("category")
            .orElseGet(() -> {
                Category a = new Category();
                a.setName("category");
                return categoryRepository.save(a);
            });
        categoryRepository.save(category);
        activity = activityRepository.findByName("activity")
                .orElseGet(() -> {
                    Activity a = new Activity();
                    a.setName("activity");
                    return activityRepository.save(a);
                });
        activityRepository.save(activity);
        product = new Product();
        product.setName("iPhone");
        product.setCategory(category);
        product.setActivity(activity);
        product.setPrice(BigDecimal.valueOf(50000));
        product.setStock(10);
        productRepository.save(product);
        role = roleRepository.findByTitle("ROLE_USER")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setTitle("ROLE_USER");
                    return roleRepository.save(r);
                });

        testUser = userRepository.findByEmail("test@gmail.com")
                .orElseGet(() -> {
                    User u = new User();
                    u.setEmail("test@gmail.com");
                    u.setPassword("0000");
                    u.setRole(role);
                    return userRepository.save(u);
                });
        cart = new Cart();
        cart.setStatus(CartStatus.ACTIVE);
        cart.setCartItem(new ArrayList<>());
        cart.setUser(testUser);

        cartRepository.save(cart);
        cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);
        cart.getCartItem().add(cartItem);
        cartRepository.save(cart);

    }

    @Test
    void shouldPlaceOrderAndReduceStockSuccessfully() throws Exception {
        CheckoutRequestDto requestDto = new CheckoutRequestDto("kerala");
        mockMvc.perform(post("/api/orders/checkout")
                        .with(csrf())
                        .with(user(new CustomUserDetails(testUser)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").exists());

        List<Order> orders = orderRepository.findAll();
        Product updatedProduct =
                productRepository.findById(product.getProductId()).orElseThrow();
        assertEquals(8, updatedProduct.getStock());
        assertEquals(1,orders.size());
    }

    @Test
    void shouldFailCheckout_whenStockIsInsufficient() throws Exception {
        CheckoutRequestDto requestDto = new CheckoutRequestDto("kerala");
        long before = orderRepository.count();
        product.setStock(1);
        productRepository.save(product);
        mockMvc.perform(post("/api/orders/checkout")
                        .with(csrf())
                        .with(user(new CustomUserDetails(testUser)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
        Product updatedProduct =
                productRepository.findById(product.getProductId()).orElseThrow();
        long after = orderRepository.count();

        assertEquals(1, updatedProduct.getStock());
        assertEquals(before,after);
    }

}
