package com.trevora.ecommerce.admin.product.controller;

import com.trevora.ecommerce.admin.product.dto.AdminAddProductRequestDto;
import com.trevora.ecommerce.admin.product.dto.AdminAddProductResponseDto;
import com.trevora.ecommerce.admin.product.dto.AdminProductResponseDto;
import com.trevora.ecommerce.admin.product.dto.AdminUpdateProductRequestDto;
import com.trevora.ecommerce.admin.product.orchestrator.AdminProductOrchestrator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/products")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "product management")
public class AdminProductController {
    private final AdminProductOrchestrator adminProductOrchestrator;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminAddProductResponseDto addProduct(@Valid @RequestBody AdminAddProductRequestDto request){
        return adminProductOrchestrator.addProduct(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<AdminProductResponseDto> getAllProducts(
            @ParameterObject Pageable pageable,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String activity){
        return adminProductOrchestrator.getAllProducts(pageable,category,activity);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void   deleteProduct(@PathVariable Long productId){
       adminProductOrchestrator.deleteProduct(productId);
    }

    @PatchMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public AdminAddProductResponseDto updateProduct(
            @PathVariable Long productId,
            @RequestBody AdminUpdateProductRequestDto request){
        return  adminProductOrchestrator.updateProduct(productId,request);
    }

}
