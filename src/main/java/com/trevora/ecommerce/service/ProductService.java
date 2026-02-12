package com.trevora.ecommerce.service;

import com.trevora.ecommerce.dto.ProductResponseDto;
import com.trevora.ecommerce.entity.Product;
import com.trevora.ecommerce.exception.ProductNotFoundException;
import com.trevora.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public Page<ProductResponseDto> getProducts(int page, int size, Sort sort, String category, String activity, String search) {
        PageRequest pageRequest = PageRequest.of(page,size,sort);
       Page<Product> productPage;
       if(category!=null && !category.isBlank()){
           productPage = productRepository.findByCategory_Name(category,pageRequest);
       }
       else if(activity!=null && !activity.isBlank()){
           productPage = productRepository.findByActivity_Name(activity,pageRequest);
       }
       else if(search!=null && !search.isBlank()){
           productPage = productRepository.findByNameContainingIgnoreCase(search,pageRequest);
       }
       else {
           productPage = productRepository.findAll(pageRequest);
       }
              return productPage.map(product -> new ProductResponseDto(
                      product.getProductId(),
                      product.getName(),
                      product.getPrice(),
                      product.getStock(),
                      product.getImage(),
                      product.getCategory().getName(),
                      product.getActivity().getName()
              ));
    }

    public ProductResponseDto productById(Long productId) {
        Product product= productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        return new ProductResponseDto(
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getImage(),
                product.getCategory().getName(),
                product.getActivity().getName()
        );

    }
}
