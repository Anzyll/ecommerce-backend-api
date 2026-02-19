package com.trevora.ecommerce.order.repository;

import com.trevora.ecommerce.common.enums.OrderStatus;
import com.trevora.ecommerce.order.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @EntityGraph(attributePaths = {"user","items","items.product"})
    List<Order> findAllByUser_UserId_OrderByCreatedAtDesc(Long userUserId);
    @Query("SELECT COALESCE(SUM(oi.quantity),0) c FROM  Order o JOIN  o.items oi WHERE o.status=:status ")
    Integer getTotalProductsSold(@Param("status") OrderStatus status);
    @Query("SELECT  COALESCE(SUM(o.totalAmount),0) FROM Order o WHERE o.status=:status" )
    Integer getTotalRevenue(@Param("status") OrderStatus status);
}
