package com.ecommerce.vichika.service;

import com.ecommerce.vichika.dto.ProductDto;
import com.ecommerce.vichika.entities.Product;
import com.ecommerce.vichika.exceptions.ResourceNotFoundExceptions;
import com.ecommerce.vichika.mapper.ProductMapper;
import com.ecommerce.vichika.repository.CategoryRepository;
import com.ecommerce.vichika.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAllProducts(Byte categoryId) {
        List<Product> products;
        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        }else {
            products = productRepository.findAll();
        }
        return products
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    public ProductDto createProduct(ProductDto productDto) {
        var category = categoryRepository.findById(productDto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundExceptions("Category not found"));
        var product = productMapper.toEntity(productDto);
        product.setCategory(category);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    public ProductDto updateProduct(ProductDto productDto,Long productId) {
        var category = categoryRepository.findById(productDto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundExceptions("Category not found"));
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Category not found"));
        productMapper.updateProduct(productDto, product);
        product.setCategory(category);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    public void deleteProduct(Long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Product not found"));
        productRepository.delete(product);
    }

}
