package com.uospd.springweb1209.services;

import com.uospd.springweb1209.entities.Category;
import com.uospd.springweb1209.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getById(Long id){
        return categoryRepository.getOne(id);
    }

    public void createNewCategory(String name){
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }
}
