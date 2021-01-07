package com.uospd.springweb1209.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.sql.Date;

@Table(name = "reviews")
@Entity
public class Review{
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "author")
    private User author;

    @Column
    @Type(type = "org.hibernate.type.TextType")
    @Size(min = 100,max = 100000,message = "Review should contains atleast 100 characters")
    private String text;

    @Column
    @Max(5)
    private int rating = 0;

    @Column
    private Date date;

    @ManyToOne(cascade =CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    private Product product;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    public Review(){}

    @Override
    public String toString() {
        return "Review{" +
                "author='" + author + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", product=" + product +
                '}';
    }
}
