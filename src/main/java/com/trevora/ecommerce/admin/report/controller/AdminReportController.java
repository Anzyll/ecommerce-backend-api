package com.trevora.ecommerce.admin.report.controller;

import com.trevora.ecommerce.admin.report.dto.AdminReportOrderResponseDto;
import com.trevora.ecommerce.admin.report.orchestrator.AdminReportOrchestrator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/reports")
@Tag(name = "Report")
public class AdminReportController {
    private final AdminReportOrchestrator adminReportOrchestrator;
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public Page<AdminReportOrderResponseDto> getAllOrders(
            @ParameterObject Pageable pageable){
        return adminReportOrchestrator.getAllOrders(pageable);
    }
}
