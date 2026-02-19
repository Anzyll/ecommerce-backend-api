package com.trevora.ecommerce.admin.product.orchestrator;

import com.trevora.ecommerce.admin.product.dto.AdminAddProductRequestDto;
import com.trevora.ecommerce.admin.product.dto.AdminAddProductResponseDto;
import com.trevora.ecommerce.admin.product.dto.AdminProductResponseDto;
import com.trevora.ecommerce.admin.product.dto.AdminUpdateProductRequestDto;
import com.trevora.ecommerce.admin.product.service.AdminProductService;
import com.trevora.ecommerce.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProductOrchestrator {
    private final AdminProductService adminProductService;
    public AdminAddProductResponseDto addProduct(AdminAddProductRequestDto request) {
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setImage(request.imageUrl());
        product.setDescription(request.description());

        Product savedProduct = adminProductService.addProduct(product,request.categoryId(),request.activityId());
        return new AdminAddProductResponseDto(
                savedProduct.getProductId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                savedProduct.getStock(),
                savedProduct.getImage(),
                savedProduct.getDescription(),
                savedProduct.getCategory().getName(),
                savedProduct.getActivity().getName(),
                savedProduct.getCreatedAt()
        );
    }

    public Page<AdminProductResponseDto> getAllProducts(Pageable pageable, String category, String activity) {
     return  adminProductService.getAllProducts(pageable,category,activity)
               .map(product ->  new AdminProductResponseDto(
                       product.getProductId(),
                       product.getName(),
                       product.getPrice(),
                       product.getStock(),
                       product.getImage(),
                       product.getCategory().getName(),
                       product.getActivity().getName()
               ));
    }

    public void deleteProduct(Long productId) {
          adminProductService.deleteProduct(productId);
    }

    public AdminAddProductResponseDto updateProduct(Long productId, AdminUpdateProductRequestDto request) {
       Product product = adminProductService.updateProduct(productId,request);

       return new AdminAddProductResponseDto(
               product.getProductId(),
               product.getName(),
               product.getPrice(),
               product.getStock(),
               product.getImage(),
               product.getDescription(),
               product.getCategory().getName(),
               product.getActivity().getName(),
               product.getCreatedAt()
       );
    }
}
