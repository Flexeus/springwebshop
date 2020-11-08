package com.uospd.springweb1209.utils;

import com.uospd.springweb1209.entities.OrderItem;
import com.uospd.springweb1209.entities.Product;
import com.uospd.springweb1209.services.ProductService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION,proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart {

    private List<OrderItem> cartItems = new ArrayList<>();
    ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void addProduct(Product product,int count){
        if(product == null || product.getAvailable()<1) return;
        OrderItem orderItem = getItemByProductId(product.getId());
        if(orderItem == null) {
            orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setCount(count);
            orderItem.setPrice(product.getPrice());
            cartItems.add(orderItem);
            return;
        }
        if (count+orderItem.getCount() > product.getAvailable()) {
            count = product.getAvailable();
            orderItem.setCount(count);
        } else orderItem.incCount(count);
    }


    public void updateItemCount(Long productId,Integer count){
        if(productId == null) return;
        Product product = productService.getProductByID(productId);
        if(product == null) return;;
        if(count>product.getAvailable()) {
            count = product.getAvailable();
        }
        else if(count<=0) count = 1;
        getItemByProductId(productId).setCount(count);
    }


    public List<OrderItem> getCartItems() {
        return cartItems;
    }

    public List<Product> getCartProducts(){
        return cartItems.stream().map(cartItem-> cartItem.getProduct()).collect(Collectors.toList());
    }

    public void removeProduct(Long productId) {
        cartItems.remove(getItemByProductId(productId));
    }

    public OrderItem getItemByProductId(Long productId){
        for (OrderItem item : cartItems) {
            if(item.getProduct().getId() == productId) return item;
        }
        return null;
    }

    public int getCartItemsCount(){
        return cartItems.stream().mapToInt( item -> item.getCount()).sum();
    }

    public Integer getCartPrice(){
        return cartItems.stream().mapToInt( item -> item.getCount()*item.getProduct().getPrice()).sum();
    }
}
