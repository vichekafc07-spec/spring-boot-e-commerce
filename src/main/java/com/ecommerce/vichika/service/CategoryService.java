package com.ecommerce.vichika.service;

import com.ecommerce.vichika.dto.CategoryDto;
import com.ecommerce.vichika.exceptions.ResourceNotFoundExceptions;
import com.ecommerce.vichika.mapper.CategoryMapper;
import com.ecommerce.vichika.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDto create(CategoryDto categoryDto) {
        var category = categoryMapper.toEntity(categoryDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public List<CategoryDto> getAllCategory(){
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public CategoryDto updateCategory(CategoryDto categoryDto,Byte id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Category not found"));
        categoryMapper.updateCategory(categoryDto, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public void deleteCategory(Byte id) {
        categoryRepository.deleteById(id);
    }

}
