package com.uospd.springweb1209.services;

import com.uospd.springweb1209.entities.Category;
import com.uospd.springweb1209.exceptions.EntityAlreadyExistException;
import com.uospd.springweb1209.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.validation.constraints.Null;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getById(Long id){
        return categoryRepository.getOne(id);
    }

    public void createCategory(Category category) throws EntityAlreadyExistException{
        if(category == null || category.getName() == null) throw new NullPointerException("Category or category name cannot be null");
        if(categoryAlreadyExist(category)) throw new EntityAlreadyExistException(String.format("Unable to create category %s.Category already exist.",category.getName()));
        categoryRepository.save(category);
    }

    public boolean categoryAlreadyExist(Category category){
        return categoryRepository.getCategoryByName(category.getName()).isPresent();
    }
}
