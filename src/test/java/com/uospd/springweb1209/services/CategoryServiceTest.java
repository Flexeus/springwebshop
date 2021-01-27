package com.uospd.springweb1209.services;

import com.uospd.springweb1209.entities.Category;
import com.uospd.springweb1209.repositories.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryServiceTest {
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;
    Category category;

    @BeforeAll
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        category = new Category("CategoryName");
        when(categoryRepository.getCategoryByName("CategoryName")).thenReturn(Optional.of(category));
    }
//
//    @Test
//    public void testAlreadyExistCategory(){
//        categoryService.createCategory(category.getName());
//    }

    @AfterEach
    void tearDown() {
    }
}