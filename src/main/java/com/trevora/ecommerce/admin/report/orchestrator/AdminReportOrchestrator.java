package com.trevora.ecommerce.admin.report.orchestrator;

import com.trevora.ecommerce.admin.report.dto.AdminReportOrderItemResponseDto;
import com.trevora.ecommerce.admin.report.dto.AdminReportOrderResponseDto;
import com.trevora.ecommerce.admin.report.service.AdminReportService;
import com.trevora.ecommerce.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminReportOrchestrator {
    private final AdminReportService adminReportService;
    public Page<AdminReportOrderResponseDto> getAllOrders(Pageable pageable) {
      Page<Order> orders = adminReportService.getAllOrders(pageable);
      return orders.map(order -> new AdminReportOrderResponseDto(
              order.getOrderId(),
              order.getItems().stream().map(orderItem -> new AdminReportOrderItemResponseDto(
                      orderItem.getOrderItemId(),
                      orderItem.getOrder().getOrderId(),
                      orderItem.getProduct().getName(),
                      orderItem.getQuantity(),
                      orderItem.getPrice()
              ))
                      .toList(),
              order.getStatus().name(),
              order.getTotalAmount(),
              order.getShippingAddress(),
              order.getCreatedAt()
      ));
    }

    public Integer getTotalProductsSold() {
       return adminReportService.getTotalProductsSold();
    }

    public Integer getTotalRevenue() {
       return adminReportService.getTotalRevenue();
    }
}
