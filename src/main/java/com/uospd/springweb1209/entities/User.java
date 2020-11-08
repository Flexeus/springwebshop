package com.uospd.springweb1209.entities;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User { //TODO: Валидацию в форме сделать
    @Id
    @Column(name = "username")
    @Size(min = 2,max = 25)
    private String username;

    @Column(name = "password")
    @Size(min = 6)
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private Set<Authority> authorities;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @Column
    @Size(min = 2,max = 25)
    private String firstname;

    @Column
    @Size(min = 2,max = 25)
    private String lastname;

    @Column
    @Email
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(Authority authority){
        authorities.add(authority);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
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