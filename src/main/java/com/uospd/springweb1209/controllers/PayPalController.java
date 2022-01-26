package com.uospd.springweb1209.controllers;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.uospd.springweb1209.entities.Order;
import com.uospd.springweb1209.entities.User;
import com.uospd.springweb1209.services.OrderService;
import com.uospd.springweb1209.services.PayPalService;
import com.uospd.springweb1209.services.UserService;
import com.uospd.springweb1209.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Controller
public class PayPalController{

    @Autowired private PayPalService service;
    @Autowired private ShoppingCart cart;
    @Autowired private UserService userService;
    @Autowired private OrderService orderService;

    private static final String SUCCESS_URL = "pay/success";
    private static final String CANCEL_URL = "pay/cancel";


    @PostMapping("/pay")
    public String payment(Principal principal, String deliveryAddress, RedirectAttributes redirectAttributes) {
        if(principal == null || deliveryAddress == null)  return "redirect:/";
        Optional<User> user = userService.findByUsername(principal.getName());
        if(cart.getCartItems().isEmpty() || user.isEmpty() )  return "redirect:/";
        Order order = orderService.createOrder(user.get(), cart.getCartItems(), deliveryAddress);

        try {
            Payment payment = service.createPayment(order.getPrice(), "", "http://localhost:80/" + CANCEL_URL, "http://localhost:80/" + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay(Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        Order order = user.getOrders().get(user.getOrders().size()-1);
        orderService.cancelOrder(order);
        return "pay_cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, Principal principal) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                User user = userService.findByUsername(principal.getName()).get();
                Order order = user.getOrders().get(user.getOrders().size()-1);
                order.setStatus(Order.OrderState.SHIPPING);
                orderService.saveOrder(order);
                return "success";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

}
