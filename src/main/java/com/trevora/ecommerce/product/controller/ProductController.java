package com.trevora.ecommerce.product.controller;

import com.trevora.ecommerce.product.dto.ProductResponseDto;
import com.trevora.ecommerce.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Catalog", description = "Browse and Search Products with pagination and filter")
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public Page<ProductResponseDto> getProducts(
            @ParameterObject Pageable pageable,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String activity,
            @RequestParam(required = false) String search
    ){
       return productService.getProducts(pageable,category,activity,search);
    }
    @GetMapping("/{productId}")
    public ProductResponseDto getProductById(@PathVariable Long productId){
        return productService.productById(productId);
    }
}
