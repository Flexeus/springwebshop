package com.uospd.springweb1209.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date date;

    @Getter @Setter
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "username")
    @Setter @Getter
    private User user;

    @Getter @Setter
    @Column(name = "delivery_address")
    @NotNull
    private String DeliveryAddress;

    @Setter @Getter
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderState status;

    public Order() { }

    public int getOrderPrice(){
        return items.stream().mapToInt(x -> x.getCount()*x.getProduct().getPrice()).sum();
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
