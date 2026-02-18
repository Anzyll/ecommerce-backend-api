package com.trevora.ecommerce.product.service;

import com.trevora.ecommerce.admin.exception.CategoryNotFoundException;
import com.trevora.ecommerce.product.entity.Category;
import com.trevora.ecommerce.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public Category getCategoryById(Long categoryId) {
       return categoryRepository.findById(categoryId)
               .orElseThrow(CategoryNotFoundException::new);
    }
}
