package com.uospd.springweb1209.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Setter(AccessLevel.PRIVATE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Setter(AccessLevel.PRIVATE)
    private Date date;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "username",nullable = false)
    private User user;

    @Column(name = "delivery_address",nullable = false)
    @NotNull
    private String DeliveryAddress;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderState status;

    private double price;


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
                ", DeliveryAddress='" + DeliveryAddress + '\'' +
                ", status=" + status +
                '}';
    }
}
