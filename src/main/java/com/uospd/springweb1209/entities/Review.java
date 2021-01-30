package com.uospd.springweb1209.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Table(name = "reviews")
@Entity
public class Review{
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "author")
    @Getter @Setter
    private User author;

    @Column
    @Type(type = "org.hibernate.type.TextType")
    @Size(min = 100,max = 100000,message = "Review should contains atleast 100 characters")
    @NotNull
    @Getter @Setter
    private String text;

    @Column
    @Getter @Setter
    @Min(0) @Max(5)
    private int rating = 0;

    @Column
    @CreationTimestamp
    @DateTimeFormat
    @Getter
    private Date date;


    @ManyToOne(cascade =CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    @Getter @Setter
    private Product product;

    public Review(){}

    @Override
    public String toString() {
        return "Review{" +
                "author='" + author.getUsername() + '\'' +
                ", text='" + text + '\'' +
                ", rating='" + rating + '\'' +
                ", date=" + date +
                ", product=" + product.getTitle() +
                '}';
    }
}
