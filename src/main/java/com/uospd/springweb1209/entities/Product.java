package com.uospd.springweb1209.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@NoArgsConstructor
@Entity
@Table(name = "products")
@Getter
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @Size(min = 3,max = 30,message = "Title should be between 3 and 30 characters")
    @NotNull
    @Setter
    private String title;

    @Column(name = "price")
    @Min(value = 1,message = "Price cannot be lesser than 1")
    @Max(value = 100_000,message = "Price cannot be more than 100000")
    @Setter
    private int price;

    @Column
    @Type(type = "org.hibernate.type.TextType")
    @Size(min = 10,max = 2000,message = "Product description should be between 10 and 2000 characters")
    @Setter
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @Setter
    private Category category;

    @Lob
    @Column(name = "image") //,columnDefinition = "MEDIUMBLOB"
    @Type(type = "org.hibernate.type.ImageType")
    @NotNull
    @Setter @Getter(AccessLevel.PRIVATE)
    private byte[] image;

    @Column
    @Setter
    @Min(0)
    @Max(100000)
    private int available;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    @Setter
    private List<Review> reviews;

    public Product(String title, int price) {
        this.title = title;
        this.price = price;
    }

    public double getAverageRating(){
        if (reviews == null || reviews.isEmpty()) return 0;
        return reviews.stream().map(x -> x.getRating()).filter(x -> x != 0).mapToInt(x -> x).average().orElse(0);
    }

    public String getBase64Image() {
        return Base64.getEncoder().encodeToString(image);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(o.hashCode() != this.hashCode()) return false;
        Product product = (Product) o;
        return  id.equals(product.id) && price == product.price &&
                title.equals(product.title) &&
                description.equals(product.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(price,title);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

}
