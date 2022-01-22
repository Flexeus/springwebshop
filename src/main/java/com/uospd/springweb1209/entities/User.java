package com.uospd.springweb1209.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements UserDetails{
    @Id
    @Column(name = "username")
    @Size(min = 2,max = 30,message = "Name should be between 2 and 30 characters")
    private String username;

    @Size(min = 5,message = "Password should have at least 5 characters")
    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private Set<Authority> authorities = new HashSet<>();

    @Column
    @Size(min = 2,max = 25)
    private String firstname;

    @Column
    @Size(min = 2,max = 25)
    private String lastname;

    @Column
    @Email(message = "Incorrect email")
    @Size(min = 4, message = "Incorrect email")
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private Set<Review> reviews;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void addAuthority(Authority authority){
        authorities.add(authority);
    }

    public boolean isAccountNonExpired(){
        return true;
    }

    public boolean isAccountNonLocked(){
        return true;
    }

    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", authorities=" + authorities +
                ", orders=" + orders +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}