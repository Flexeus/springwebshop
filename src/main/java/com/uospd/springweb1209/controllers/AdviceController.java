package com.uospd.springweb1209.controllers;

import com.uospd.springweb1209.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@AllArgsConstructor
@ControllerAdvice
public class AdviceController {

    private CategoryService categoryService;

    @ModelAttribute
    public void addAtributes(Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
    }

}
