package com.uospd.springweb1209.controllers;

import com.uospd.springweb1209.entities.Product;
import com.uospd.springweb1209.services.OrderService;
import com.uospd.springweb1209.services.ProductService;
import com.uospd.springweb1209.services.UserService;
import com.uospd.springweb1209.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ShoppingCart cart;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String showCart(Model model){
        model.addAttribute("items",cart.getCartItems());
        model.addAttribute("itemsCount",cart.getCartItemsCount());
        model.addAttribute("cost",cart.getCartPrice());
        return "cart";
    }

    @PostMapping("/add/{id}")
    public String addProductToCart(@PathVariable Long id,@RequestParam(required = false,defaultValue = "1") int count){
        Product product = productService.getProductByID(id);
        cart.addProduct(product,count);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateCart(Long productId, @RequestParam(defaultValue = "1") Integer count){
        cart.updateItemCount(productId,count);
        return "redirect:/cart";
    }

    @PostMapping("/delete/{id}")
    public String deleteFromCart(@PathVariable Long id) {
        cart.removeProduct(id);
        return "redirect:/cart";
    }
}
