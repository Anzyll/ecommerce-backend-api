package com.trevora.ecommerce.service;

import com.trevora.ecommerce.admin.report.service.AdminReportService;
import com.trevora.ecommerce.common.enums.OrderStatus;
import com.trevora.ecommerce.order.entity.Order;
import com.trevora.ecommerce.order.repository.OrderRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminReportServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private AdminReportService adminReportService;

    @Test
    void getAllOrders_shouldReturnPagedOrders(){
        Pageable pageable = PageRequest.of(0,10);
        Page<Order> mockPage = new PageImpl<>(List.of(new Order()));
        when(orderRepository.findAll(any(Pageable.class)))
                .thenReturn(mockPage);
        Page<Order> result = adminReportService.getAllOrders(pageable);
           assertEquals(1,result.getTotalElements());
           verify(orderRepository).findAll(any(Pageable.class));
    }

    @Test
    void getTotalRevenue_shouldReturnTotalRevenueGenerated(){
        when(orderRepository.getTotalRevenue(OrderStatus.PAID))
                .thenReturn(10000);
        int result = adminReportService.getTotalRevenue();
        assertEquals(10000,result);
        verify(orderRepository).getTotalRevenue(OrderStatus.PAID);
    }

    @Test
    void getTotalProductSold_shouldReturnTotalProductsSoldCount(){
        when(orderRepository.getTotalRevenue(OrderStatus.PAID))
                .thenReturn(10000);
        int result = adminReportService.getTotalRevenue();
        assertEquals(10000,result);
        verify(orderRepository).getTotalRevenue(OrderStatus.PAID);
    }

    @Test
    void getTotalProductsSold_shouldReturnCountOfPaidOrders(){
        when(orderRepository.getTotalProductsSold(OrderStatus.PAID))
                .thenReturn(30);
        int result = adminReportService.getTotalProductsSold();
        assertEquals(30,result);
        verify(orderRepository).getTotalProductsSold(OrderStatus.PAID);

    }


}
