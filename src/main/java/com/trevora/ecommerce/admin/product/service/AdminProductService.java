package com.trevora.ecommerce.admin.product.service;

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
}
