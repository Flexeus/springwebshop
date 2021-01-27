package com.uospd.springweb1209.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date date;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @Column(name = "delivery_address")
    private String DeliveryAddress;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderState status;

    public Order() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public int getOrderPrice(){
        return items.stream().mapToInt(x -> x.getCount()*x.getProduct().getPrice()).sum();
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

    public Date getDate() {
        return date;
    }

    public OrderState getStatus() {
        return status;
    }

    public String getDeliveryAddress() {
        return DeliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        DeliveryAddress = deliveryAddress;
    }

    public void setStatus(OrderState status) {
        this.status = status;
    }

    public enum OrderState{
        CANCELED,PROCESSING,SHIPPING,COMPLETED;
        @Override
        public String toString() {
            return switch(this){
                case CANCELED -> "Order canceled";
                case PROCESSING -> "Order is being processed";
                case SHIPPING -> "Order was shipped";
                case COMPLETED->"Order completed";
            };
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", items=" + items +
                ", user=" + user.getUsername() +
                ", DeliveryAddress='" + DeliveryAddress + '\'' +
                ", status=" + status +
                '}';
    }
}
