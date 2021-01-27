package com.uospd.springweb1209.controllers;


import com.uospd.springweb1209.entities.Category;
import com.uospd.springweb1209.entities.Product;
import com.uospd.springweb1209.entities.User;
import com.uospd.springweb1209.exceptions.EntityAlreadyExistException;
import com.uospd.springweb1209.services.CategoryService;
import com.uospd.springweb1209.services.ProductService;
import com.uospd.springweb1209.services.UserService;
import com.uospd.springweb1209.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class MainController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    ShoppingCart cart;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/registration")
    public String registerPage(User user){
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@Valid @ModelAttribute User user, BindingResult bindingResult){
        if(userService.userExist(user.getUsername())) bindingResult.rejectValue("username",null,"User already registered!");
        if(userService.emailExist(user.getEmail())) bindingResult.rejectValue("email",null,"User with this email already registered!");
        if(bindingResult.hasErrors()) return "registration";
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping({"/","/index"})
    public String shopPage(@RequestParam(required = false,defaultValue = "desc") String order, Model model, @PageableDefault(size = 10,sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Sort sort = order.equals("asc") ? pageable.getSort().ascending() : pageable.getSort().descending();
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Product> page = productService.getAllProductsPage(pageable);
        model.addAttribute("page", page);
        model.addAttribute("cartProducts",cart.getCartProducts());
        return "index";
    }


    @GetMapping("/categories/{categoryId}")
    public String categoryPage(@PathVariable Long categoryId, @RequestParam(required = false,defaultValue = "desc") String order, Model model,@PageableDefault(size = 15,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        Sort sort = order.equals("asc") ? pageable.getSort().ascending() : pageable.getSort().descending();
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Product> productsByCategoryId = productService.getAllProductsByCategoryId(categoryId, pageable);
        model.addAttribute("page",productsByCategoryId);
        model.addAttribute("cartProducts",cart.getCartProducts());
        return "index";
    }

    @GetMapping("/categories/create")
    public String createCategoryPage(Category category){
        return "create_category";
    }

    @PostMapping("/categories/create")
    public String createCategory(@Valid @ModelAttribute Category category,BindingResult bindingResult){
        try {
            categoryService.createCategory(category);
        } catch (EntityAlreadyExistException e) {
            bindingResult.rejectValue("name",null,"This category already exist");
        }
        if(bindingResult.hasErrors()){
            return "create_category";
        }
        return "redirect:/";
    }

    @GetMapping("/search")
    public String greetingSubmit(@RequestParam(value = "text") String search,@RequestParam(required = false,defaultValue = "desc") String order, Model model,@PageableDefault(size = 6,sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        if(search == null) return "redirect:/";
        Sort sort = order.equals("asc") ? pageable.getSort().ascending() : pageable.getSort().descending();
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Product> page = productService.findProductsByTitle(search,pageable);
        model.addAttribute("page", page);
        model.addAttribute("cartProducts",cart.getCartProducts());
        return "index";
    }

}
