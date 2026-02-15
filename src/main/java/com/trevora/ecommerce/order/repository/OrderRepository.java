package com.trevora.ecommerce.order.repository;

import com.trevora.ecommerce.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUser_UserId_OrderByCreatedAtDesc(Long userUserId);
}
