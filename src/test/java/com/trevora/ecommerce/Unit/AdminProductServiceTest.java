package com.trevora.ecommerce.Unit;

import com.trevora.ecommerce.admin.product.dto.AdminUpdateProductRequestDto;
import com.trevora.ecommerce.admin.product.service.AdminProductService;
import com.trevora.ecommerce.product.entity.Activity;
import com.trevora.ecommerce.product.entity.Category;
import com.trevora.ecommerce.product.entity.Product;
import com.trevora.ecommerce.product.exception.ProductNotFoundException;
import com.trevora.ecommerce.product.repository.ProductRepository;
import com.trevora.ecommerce.product.service.ActivityService;
import com.trevora.ecommerce.product.service.CategoryService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminProductServiceTest {
    @Mock
    private  ProductRepository productRepository;
    @Mock
    private  CategoryService categoryService;
    @Mock
    private  ActivityService activityService;
    @InjectMocks
    private AdminProductService adminProductService;


    @Test
    void addProduct_shouldSetCategoryAndActivityAndSave(){
        Category category = new Category();
        category.setCategoryId(1L);
        Activity activity = new Activity();
        activity.setActivityId(2L);
        Product product = new Product();
        product.setProductId(3L);
        product.setCategory(category);
        product.setActivity(activity);
       when(categoryService.getCategoryById(1L))
               .thenReturn(category);

        when(activityService.getActivityById(2L))
                .thenReturn(activity);
        when(productRepository.save(any(Product.class)))
                .thenAnswer(Invocation->Invocation.getArgument(0));

        Product result = adminProductService.addProduct(product,1L,2L);
        assertNotNull(result);
        assertEquals(category,result.getCategory());
        assertEquals(activity,result.getActivity());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteProduct_shouldThrowWhenNotFound(){
        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,()->adminProductService.deleteProduct(1L));
    }

    @Test
    void updateProduct_shouldThrowWhenNotFound() {
        AdminUpdateProductRequestDto request =
                new AdminUpdateProductRequestDto(
                        null, null, null, null, null, null, null
                );
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,
                () -> adminProductService.updateProduct(1L, request));
    }

    @Test
    void deleteProduct_should_DeleteProduct(){
        Product product = new Product();
        product.setProductId(1L);
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));
       adminProductService.deleteProduct(1L);
        verify(productRepository).delete(product);

    }

    @Test
    void updateProduct_shouldUpdateProvidedFieldsOnly() {
        Product product = new Product();
        product.setProductId(1L);
        product.setName("Old Name");
        product.setStock(5);

        Category category = new Category();
        category.setCategoryId(2L);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));
        when(categoryService.getCategoryById(2L))
                .thenReturn(category);

        AdminUpdateProductRequestDto request =
                new AdminUpdateProductRequestDto(
                        "New Name",
                        null,
                        10,
                        null,
                        null,
                        2L,
                        null
                );
        Product result = adminProductService.updateProduct(1L,request);
        assertNotNull(result);
        assertEquals("New Name",product.getName());
        assertEquals(10,product.getStock());
        assertEquals(2L,product.getCategory().getCategoryId());
    }

}
