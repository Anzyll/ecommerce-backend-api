package com.trevora.ecommerce.repository;

import com.trevora.ecommerce.Integration.BaseIntegrationTest;
import com.trevora.ecommerce.common.entity.Role;
import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.common.enums.OrderStatus;
import com.trevora.ecommerce.order.entity.Order;
import com.trevora.ecommerce.order.entity.OrderItem;
import com.trevora.ecommerce.order.repository.OrderRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import com.trevora.ecommerce.product.entity.Activity;
import com.trevora.ecommerce.product.entity.Category;
import com.trevora.ecommerce.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.math.BigDecimal;

@DataJpaTest
public class OrderRepositoryTest extends BaseIntegrationTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setup() {
        Role role = testEntityManager .getEntityManager()
                .createQuery( "select r from Role r where r.title = :title", Role.class )
                .setParameter("title", "ROLE_USER") .getSingleResult();

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("0000");
        user.setRole(role);
        testEntityManager.persist(user);

        Activity activity = new Activity();
        activity.setName("test activity");
        testEntityManager.persist(activity);

        Category category = new Category();
        category.setName("test category");
        testEntityManager.persist(category);

        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(500));
        product.setActivity(activity);
        product.setCategory(category);
        testEntityManager.persist(product);

        Order order1 = new Order();
        order1.setStatus(OrderStatus.PAID);
        order1.setTotalAmount(BigDecimal.valueOf(1000));
        order1.setUser(user);
        order1.setShippingAddress("Kerala");

        OrderItem item = new OrderItem();
        item.setQuantity(2);
        item.setProduct(product);
        item.setPrice(product.getPrice());
        item.setOrder(order1);

        order1.getItems().add(item);

        testEntityManager.persist(order1);

        Order order2 = new Order();
        order2.setStatus(OrderStatus.PAID);
        order2.setTotalAmount(BigDecimal.valueOf(2000));
        order2.setUser(user);
        order2.setShippingAddress("Kerala");
        testEntityManager.persist(order2);

        Order order3 = new Order();
        order3.setStatus(OrderStatus.CREATED);
        order3.setTotalAmount(BigDecimal.valueOf(3000));
        order3.setUser(user);
        order3.setShippingAddress("Kerala");
        testEntityManager.persist(order3);

        testEntityManager.flush();
    }
    @Test
    void should_return_totalRevenue(){
        Integer totalRevenue = orderRepository.getTotalRevenue(OrderStatus.PAID);
        assertNotNull(totalRevenue);
        assertEquals(3000,totalRevenue);
    }
    @Test
    void should_return_totalProductSold(){
        Integer totalProductsSold = orderRepository.getTotalProductsSold(OrderStatus.PAID);
        assertNotNull(totalProductsSold);
        assertEquals(2,totalProductsSold);
    }

}
