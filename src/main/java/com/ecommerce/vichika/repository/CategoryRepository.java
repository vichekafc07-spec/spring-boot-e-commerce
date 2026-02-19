package com.ecommerce.vichika.repository;

import com.ecommerce.vichika.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Byte> {
}