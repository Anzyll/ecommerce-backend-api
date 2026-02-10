package com.trevora.ecommerce.controller;

import com.trevora.ecommerce.dto.ProductResponseDto;
import com.trevora.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public Page<ProductResponseDto> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue ="3") int size,
            Sort sort,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String activity,
            @RequestParam(required = false) String search
    ){
       return productService.getProducts(page,size, sort,category,activity,search);
    }
    @GetMapping("/{productId}")
    public ProductResponseDto getProductById(@PathVariable Long productId){
        return productService.productById(productId);
    }
}
