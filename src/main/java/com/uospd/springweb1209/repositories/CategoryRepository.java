package com.uospd.springweb1209.repositories;

import com.uospd.springweb1209.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category getCategoryById(Long id);
    Optional<Category> getCategoryByName(String name);
}
