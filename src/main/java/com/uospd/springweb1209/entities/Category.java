package com.uospd.springweb1209.entities;

import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.boot.web.servlet.support.ServletContextApplicationContextInitializer;
import org.springframework.web.method.ControllerAdviceBean;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    @Size(min = 2,max = 20)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> products;

    public Category(String name) {
        this.name = name;
    }

}
