package com.uospd.springweb1209.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_count")
    private int count = 1;

    @Column
    private int price;


    public OrderItem() {
    }

    public void incCount(int count){
        this.count+=count;
    }

    public void decCount(){
        count--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;
        return product.equals(orderItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, product, count);
    }

    @Override public String toString(){
        return "OrderItem{" +
                "id=" + id +
                ", orderid=" + order.getId() +
                ", product=" + product +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
