package com.uospd.springweb1209.entities;


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
import java.util.Base64;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    private Long id;

    @Column(name = "title")
    @Size(min = 3,max = 30,message = "Title should be between 3 and 30 characters")
    @NotNull
    @Getter @Setter
    private String title;

    @Column(name = "price")
    @Min(value = 1,message = "Price cannot be lesser than 1")
    @Max(value = 100_000,message = "Price cannot be more than 100000")
    @Getter @Setter
    private int price;

    @Column
    @Type(type = "org.hibernate.type.TextType")
    @Size(min = 10,max = 2000,message = "Product description should be between 10 and 2000 characters")
    @Getter @Setter
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Getter @Setter
    Category category;

    @Lob
    @Column(name = "image") //,columnDefinition = "MEDIUMBLOB"
    @Type(type = "org.hibernate.type.ImageType")
    @NotNull
    @Setter
    private byte[] image;

    @Column
    @Getter @Setter
    @Min(0)
    @Max(100000)
    private int available;

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Getter @Setter
    private Set<Review> reviews;

    public Product(String title, int price) {
        this.title = title;
        this.price = price;
    }

    public double getAverageRating(){
        if (getReviews().isEmpty()) return 0;
        double average = reviews.stream().map(x -> x.getRating()).filter(x -> x != 0).mapToInt(x -> x).average().orElse(0);
        return average;
    }

    public byte[] getImage() {
        return image;
    }

    public String getBase64Image() {
        return Base64.getEncoder().encodeToString(image);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return  id == product.id && price == product.price &&
                title.equals(product.title) &&
                category.equals(product.category) &&
                description == product.getDescription();
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
