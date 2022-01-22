package com.uospd.springweb1209.entities;

import lombok.*;
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
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Review{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private User author;

    @Column(nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    @Size(min = 100,max = 100000,message = "Review should contains atleast 100 characters")
    @NotNull
    private String text;

    @Column
    @Min(0) @Max(5)
    private int rating = 0;

    @Column
    @CreationTimestamp
    @DateTimeFormat
    @Setter(AccessLevel.PRIVATE)
    private Date date;

    @ManyToOne(cascade =CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    private Product product;

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
