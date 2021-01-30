package com.uospd.springweb1209.controllers;

import com.uospd.springweb1209.entities.Order;
import com.uospd.springweb1209.entities.User;
import com.uospd.springweb1209.services.OrderService;
import com.uospd.springweb1209.services.UserService;
import com.uospd.springweb1209.utils.ShoppingCart;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class OrderController {
    private OrderService orderService;
    private UserService userService;
    private ShoppingCart cart;

    @PostMapping("/orders")
    public String createOrder(@RequestParam String deliveryAddress, @RequestParam String username){
        Optional<User> user = userService.findByUsername(username);
        if(cart.getCartItems().isEmpty() || user.isEmpty())  return "redirect:/";
        orderService.createOrder(user.get(), cart.getCartItems(),deliveryAddress);
        return "redirect:/";
    }

    @GetMapping("/orders/new")
    public String createOrder(Model model,Principal principal){
        if(cart.getCartItems().isEmpty())  return "redirect:/";
        model.addAttribute("username",principal.getName());
        return "order_registration";
    }

    @GetMapping("/orders/personal") // TODO:Сделать постранично
    public String ordersPage(Principal principal, Model model, @PageableDefault(size = 6,sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Optional<User> user = userService.findByUsername(principal.getName());
        if(user.isEmpty()) return "redirect:/";
        Page<Order> userOrders = orderService.getUserOrders(user.get(), pageable);
        model.addAttribute("orders",userOrders);
        return "orders";
    }

    @GetMapping("/orders/{orderId}")
    public String orderDetailed(@PathVariable Long orderId, Model model){
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order",order);
        return "order_details";
    }
}
