package com.uospd.springweb1209.controllers;

import com.uospd.springweb1209.entities.Product;
import com.uospd.springweb1209.entities.Review;
import com.uospd.springweb1209.entities.User;
import com.uospd.springweb1209.services.CategoryService;
import com.uospd.springweb1209.services.ProductService;
import com.uospd.springweb1209.services.ReviewService;
import com.uospd.springweb1209.services.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;


@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public String detailsPage(Model model,Principal principal,
                              @PathVariable("id") Long id,
                              @PageableDefault(size = 6,sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
                              @ModelAttribute Review review){
        Product selectedProduct = productService.getProductByID(id);
        if(principal != null){
            Optional<User> user = userService.findByUsername(principal.getName());
            boolean didPreview = reviewService.userDidPreview(user.get(), selectedProduct);
            model.addAttribute("didReview",didPreview);
        }
        Page<Review> reviewsPages = reviewService.getProductReviews(selectedProduct, pageable);
        model.addAttribute("selectedProduct", selectedProduct);
        model.addAttribute("reviewsPage",reviewsPages);
        return "product_page";
    }

    @Secured(value = {"ROLE_ADMIN"})
    @DeleteMapping("{id}")
    public String deleteProjectById(@PathVariable Long id){
        productService.deleteProductByID(id);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String addProductPage(@ModelAttribute Product product){
        return "add_product";
    }


    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("")
    public String addProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult, @RequestPart(required = false) MultipartFile imageFile) {
        if (bindingResult.hasErrors()) return "add_product";
        if (product.getId() == null && imageFile.isEmpty()){ // Если создаем новый продукт и изображение не установлено
            bindingResult.rejectValue("image",null,"Set up image!!!");
            return "add_product";
        }
        productService.saveOrUpdateProduct(product, imageFile);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editProductPage(Model model, @PathVariable Long id){
        model.addAttribute("product", productService.getProductByID(id));
        return "add_product";
    }

//    @Secured(value = {"ROLE_ADMIN"})
//    @PutMapping("")
//    public String postEditProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult,MultipartFile imageFile){
//        if (bindingResult.hasErrors()) return "edit_product";
//        productService.updateProduct(product,imageFile);
//        return "redirect:/products/"+product.getId();
//    }

    @PostMapping("/{productId}/addreview")
    public String detailsPage( @PathVariable Long productId, @Valid @ModelAttribute Review review, BindingResult bindingResult, Principal principal){
        if(principal == null) return "redirect:/products/"+productId;
        Optional<User> user = userService.findByUsername(principal.getName());
        if(bindingResult.hasErrors()){
            bindingResult.rejectValue("text",null,"review too short");
        }
        else if(!user.isEmpty()) reviewService.createReview(user.get(),productId,review);
        return "redirect:/products/"+productId;
    }


}
