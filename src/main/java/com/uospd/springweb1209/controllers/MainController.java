package com.uospd.springweb1209.controllers;

import com.uospd.springweb1209.entities.Product;
import com.uospd.springweb1209.entities.User;
import com.uospd.springweb1209.services.CategoryService;
import com.uospd.springweb1209.services.ProductService;
import com.uospd.springweb1209.services.UserService;
import com.uospd.springweb1209.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


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
    public String registrationPost(@ModelAttribute User user){
        System.out.println("user:"+user);
        userService.saveUser(user);
        return "redirect:/";
    }


    @GetMapping({"/","/index"})
    public String shopPage(Model model, @PageableDefault(size = 15,sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Product> page = productService.getAllProductsPage(pageable);
        model.addAttribute("page", page);
        model.addAttribute("cartProducts",cart.getCartProducts());
        return "index";
    }

    @GetMapping("/categories/{categoryId}")
    public String categoryPage(@PathVariable Long categoryId, Model model,@PageableDefault(size = 15,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        Page<Product> productsByCategoryId = productService.getAllProductsByCategoryId(categoryId, pageable);
        model.addAttribute("page",productsByCategoryId);
        model.addAttribute("cartProducts",cart.getCartProducts());
        return "index";
    }

    @GetMapping("/categories/create")
    public String createCategoryPage(){
        return "create_category";
    }


    @PostMapping("/categories/create")
    public String createCategory(@RequestParam String categoryName){
        categoryService.createNewCategory(categoryName);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String greetingSubmit(@RequestParam(value = "text") String search, Model model,@PageableDefault(size = 6,sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        if(search == null) return "redirect:/";
        Page<Product> page = productService.findProductsByTitle(search,pageable);
        model.addAttribute("page", page);
        return "index";
    }

}
