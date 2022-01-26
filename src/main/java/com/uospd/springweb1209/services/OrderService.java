package com.uospd.springweb1209.services;

import com.uospd.springweb1209.entities.Order;
import com.uospd.springweb1209.entities.OrderItem;
import com.uospd.springweb1209.entities.User;
import com.uospd.springweb1209.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public Order getOrderById(Long id){
        return orderRepository.getOne(id);
    }

    @Transactional
    public Order createOrder(User user, List<OrderItem> items, String deliveryAddress) {
        if(user == null || deliveryAddress.isEmpty()) throw new RuntimeException();
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.OrderState.PROCESSING);
        order.setDeliveryAddress(deliveryAddress);
        items.forEach(item -> {
            order.getItems().add(item);
            item.getProduct().setAvailable(item.getProduct().getAvailable()-item.getCount());
            item.setOrder(order);
            productService.saveProduct(item.getProduct());
            order.setPrice(order.getPrice()+item.getPrice());
        });
        items.clear();
        return orderRepository.save(order);
    }

    public Order saveOrder(Order order){
        return orderRepository.save(order);
    }

    public void cancelOrder(Order order){
        order.getItems().forEach(item ->{
            int available = item.getProduct().getAvailable();
            item.getProduct().setAvailable(available+item.getCount());
        });
        order.setStatus(Order.OrderState.CANCELED);
        orderRepository.save(order);
    }

    public Page<Order> getUserOrders(User user, Pageable pageable){
        return orderRepository.getAllByUser(user,pageable);
    }

    public Page<Order> findAllOrders(Pageable pageable){
        return orderRepository.findAll(pageable);
    }
}
