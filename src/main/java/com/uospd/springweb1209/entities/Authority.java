package com.uospd.springweb1209.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "username")
    @Getter
    @Setter
    private User user;

    @Column(name = "authority")
    @Getter
    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }
}