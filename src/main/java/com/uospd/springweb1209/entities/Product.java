package com.uospd.springweb1209.entities;



import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @Size(min = 3,max = 30)
    private String title;

    @Column(name = "price")
    @Min(1)
    @Max(100_000)
    private int price;

    @Column
    @Type(type = "org.hibernate.type.TextType")
    @Size(min = 1,max = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @Lob
    @Column(name = "image") //,columnDefinition = "MEDIUMBLOB"
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] image;

    @Column
    @Min(0)
    @Max(100000)
    private int available;

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
    private List<Review> reviews;

    public Product(String title, int price) {
        this.title = title;
        this.price = price;
    }


    public Product() {
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getAverageRating(){
        if (getReviews().isEmpty()) return "0";
        NumberFormat nf = new DecimalFormat("#.##");
        String format = nf.format(getReviews().stream().map(x -> x.getRating()).filter(x -> x != 0 && x != null).mapToInt(x -> x).average().getAsDouble());
        return format;
    }

    public byte[] getImage() {
        if(image == null){
            byte[] b = new byte[1];
            return b;
        }
        return image;
    }

    public String getBase64Image() {
        return Base64.getEncoder().encodeToString(image);
        //return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return  id == product.id && price == product.price &&
                title.equals(product.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price,title);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
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
