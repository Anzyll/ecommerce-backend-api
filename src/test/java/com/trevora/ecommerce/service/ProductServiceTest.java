package com.trevora.ecommerce.service;

import com.trevora.ecommerce.product.dto.ProductResponseDto;
import com.trevora.ecommerce.product.entity.Activity;
import com.trevora.ecommerce.product.entity.Category;
import com.trevora.ecommerce.product.entity.Product;
import com.trevora.ecommerce.product.exception.ProductNotFoundException;
import com.trevora.ecommerce.product.repository.ProductRepository;
import com.trevora.ecommerce.product.service.ProductService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;


    @Test
    void productById_existingProduct_shouldReturnProduct(){
        Product product = mock(Product.class);
        when(product.getProductId()).thenReturn(1L);
        when(product.getName()).thenReturn("Shoes");
        Category category = new Category();
        category.setName("Sports");
        when(product.getCategory()).thenReturn(category);
        Activity activity = new Activity();
        activity.setName("Running");
        when(product.getActivity()).thenReturn(activity);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));
        ProductResponseDto foundProduct = productService.productById(1L);
        assertEquals("Shoes",foundProduct.name());
        assertEquals(1L,foundProduct.productId());
        assertEquals("Sports",foundProduct.category());
        assertEquals("Running",foundProduct.activity());

        verify(productRepository).findById(1L);
    }

    @Test
    void productById_not_existingProduct_should_throwException(){
        when(productRepository.findById(2L))
                .thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,()->productService.productById(2L));
        verify(productRepository).findById(2L);
    }

}
