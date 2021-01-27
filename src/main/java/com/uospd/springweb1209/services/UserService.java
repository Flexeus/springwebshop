package com.uospd.springweb1209.services;

import com.uospd.springweb1209.entities.Authority;
import com.uospd.springweb1209.entities.User;
import com.uospd.springweb1209.repositories.AuthorityRepository;
import com.uospd.springweb1209.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public Optional<User> findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return Optional.ofNullable(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean userExist(String username){
        return userRepository.findByUsername(username) != null;
    }

    public boolean emailExist(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public void saveUser(User user){
        if(userExist(user.getUsername()) || emailExist(user.getEmail())) return;
        user.setEnabled(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Authority authority = new Authority("ROLE_USER");
        Set<Authority> array = Collections.singleton(authority);
        authority.setUser(user);
        authorityRepository.save(authority);
        user.setAuthorities(array);

    }
}