package com.uospd.springweb1209.controllers;

import com.uospd.springweb1209.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AdviceController {
    @Autowired
    CategoryService categoryService;

    @ModelAttribute
    public void addAtributes(Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
    }

}
