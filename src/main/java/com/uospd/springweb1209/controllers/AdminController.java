package com.uospd.springweb1209.controllers;

import com.uospd.springweb1209.entities.Order;
import com.uospd.springweb1209.entities.User;
import com.uospd.springweb1209.services.OrderService;
import com.uospd.springweb1209.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {


    UserService userService;
    OrderService orderService;

    @GetMapping("")
    public String adminPage(){
        return "admin_page";
    }

    @GetMapping("/orders")
    public String orders(Model model,@PageableDefault(size = 6,sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Order> userOrders = orderService.findAllOrders(pageable);
        model.addAttribute("orders",userOrders);
        return "orders";
    }

    @GetMapping("/users")
    public String userList(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users",users);
        return "orders";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
