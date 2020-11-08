package com.uospd.springweb1209.controllers;

import com.uospd.springweb1209.entities.Order;
import com.uospd.springweb1209.entities.User;
import com.uospd.springweb1209.services.OrderService;
import com.uospd.springweb1209.services.UserService;
import com.uospd.springweb1209.utils.ShoppingCart;
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

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    ShoppingCart cart;

    @PostMapping("/orders")
    public String createOrder(@RequestParam String deliveryAddress, @RequestParam String username){
        User user = userService.findByUsername(username);
        Order order = orderService.createOrder(user, cart.getCartItems(),deliveryAddress);
        return "redirect:/";
    }

    @GetMapping("/orders/new")
    public String createOrder(Model model,Principal principal){
        model.addAttribute("username",principal.getName());
        return "order_registration";
    }

    @GetMapping("/orders/personal") // TODO:Сделать постранично
    public String ordersPage(Principal principal, Model model, @PageableDefault(size = 6,sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        User user = userService.findByUsername(principal.getName());
        Page<Order> userOrders = orderService.getUserOrders(user, pageable);
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
