package com.trevora.ecommerce.product.controller;

import com.trevora.ecommerce.product.dto.ProductResponseDto;
import com.trevora.ecommerce.product.service.ProductService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
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
