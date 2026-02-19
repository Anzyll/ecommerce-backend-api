package com.trevora.ecommerce.admin.product.service;

import com.trevora.ecommerce.admin.product.dto.AdminUpdateProductRequestDto;
import com.trevora.ecommerce.product.entity.Activity;
import com.trevora.ecommerce.product.entity.Category;
import com.trevora.ecommerce.product.entity.Product;
import com.trevora.ecommerce.product.exception.ProductNotFoundException;
import com.trevora.ecommerce.product.repository.ProductRepository;
import com.trevora.ecommerce.product.service.ActivityService;
import com.trevora.ecommerce.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ActivityService activityService;
    public Product addProduct(Product product,Long categoryId,Long activityId) {
        Category category = categoryService.getCategoryById(categoryId);
        Activity activity= activityService.getActivityById(activityId);

        product.setCategory(category);
        product.setActivity(activity);

        Product savedProduct = productRepository.save(product);
        log.info("Admin added product name={}, categoryId={}, activityId={}",
                product.getName(), categoryId, activityId);
        return savedProduct;
    }

    public Page<Product> getAllProducts(Pageable pageable,String category,String activity) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() ,pageable.getPageSize(),pageable.getSort());
        Page<Product> productPage;
        if(category!=null && !category.isBlank()){
            productPage = productRepository.findByCategory_Name(category,pageRequest);
        }
        else if(activity!=null && !activity.isBlank()){
            productPage = productRepository.findByActivity_Name(category,pageRequest);
        }
        else {
            productPage = productRepository.findAll(pageRequest);
        }
        log.info("admin viewed products");
        return productPage;
    }

    public void deleteProduct(Long productId) {
      Product product = productRepository.findById(productId)
              .orElseThrow(ProductNotFoundException::new);
      productRepository.delete(product);
      log.info("admin deleted the product of productId={}",productId);
    }

    @Transactional
    public Product updateProduct(Long productId, AdminUpdateProductRequestDto request) {
      Product product =  productRepository.findById(productId)
              .orElseThrow(ProductNotFoundException::new);

        if (request.categoryId() != null) {
            Category category = categoryService.getCategoryById(request.categoryId());
            product.setCategory(category);
        }

        if (request.activityId() != null) {
            Activity activity = activityService.getActivityById(request.activityId());
            product.setActivity(activity);
        }
        if (request.name() != null) {
            product.setName(request.name());
        }

        if (request.price() != null) {
            product.setPrice(request.price());
        }

        if (request.stock() != null) {
            product.setStock(request.stock());
        }

        if (request.imageUrl() != null) {
            product.setImage(request.imageUrl());
        }

        if (request.description() != null) {
            product.setDescription(request.description());
        }

        log.info("admin updated product of productId={}",productId);
        return product;

    }
}
