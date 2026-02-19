package com.trevora.ecommerce.admin.report.service;

import com.trevora.ecommerce.order.entity.Order;
import com.trevora.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class AdminReportService {
    private final OrderRepository orderRepository;
    public Page<Order> getAllOrders(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort());
        Page<Order> page =  orderRepository.findAll(pageRequest);
        log.info("admin view all orders ");
        return page;
    }
}
