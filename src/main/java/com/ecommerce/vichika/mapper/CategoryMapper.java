package com.ecommerce.vichika.mapper;

import com.ecommerce.vichika.dto.CategoryDto;
import com.ecommerce.vichika.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);
    void updateCategory(CategoryDto categoryDto,@MappingTarget Category category);
}
