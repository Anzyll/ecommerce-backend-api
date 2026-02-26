package com.trevora.ecommerce.e2e;

import com.trevora.ecommerce.cart.entity.Cart;
import com.trevora.ecommerce.cart.entity.CartItem;
import com.trevora.ecommerce.cart.repository.CartItemRepository;
import com.trevora.ecommerce.cart.repository.CartRepository;
import com.trevora.ecommerce.common.entity.Role;
import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.common.enums.CartStatus;
import com.trevora.ecommerce.common.repository.RoleRepository;
import com.trevora.ecommerce.common.repository.UserRepository;
import com.trevora.ecommerce.integration.BaseIntegrationTest;
import com.trevora.ecommerce.order.dto.CheckoutRequestDto;
import com.trevora.ecommerce.order.dto.OrderResponseDto;
import com.trevora.ecommerce.order.repository.OrderRepository;
import com.trevora.ecommerce.product.entity.Activity;
import com.trevora.ecommerce.product.entity.Category;
import com.trevora.ecommerce.product.entity.Product;
import com.trevora.ecommerce.product.exception.ProductNotFoundException;
import com.trevora.ecommerce.product.repository.ActivityRepository;
import com.trevora.ecommerce.product.repository.CategoryRepository;
import com.trevora.ecommerce.product.repository.ProductRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("e2e")
public class OrderE2ETest extends BaseIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;
    Product product;
    Category category;
    Activity activity;
    Role role;
    User testUser;
    Cart cart;
    CartItem cartItem;
     Long before;
     Long after;


    @BeforeEach
    void setup() {
        category = categoryRepository.findByName("category")
                .orElseGet(() -> {
                    Category a = new Category();
                    a.setName("category");
                    return categoryRepository.save(a);
                });
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

        testUser = userRepository.findById(1L)
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
    void shouldPlaceOrder_endToEnd() {
        before = orderRepository.count();
        CheckoutRequestDto request = new CheckoutRequestDto("Kerala");
        ResponseEntity<OrderResponseDto> response = testRestTemplate.postForEntity(
                "/api/orders/checkout",request,OrderResponseDto.class
        );
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().orderId());
        assertEquals("PAID", response.getBody().status());
        after = orderRepository.count();
        assertEquals(before+1,after);
        Product updatedProduct = productRepository.findById(product.getProductId()).orElseThrow(ProductNotFoundException::new);
        assertEquals(8,updatedProduct.getStock());
    }
}
