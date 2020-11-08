package com.uospd.springweb1209.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @Column(name = "authority")
    private String authority;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }
}