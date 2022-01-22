package com.uospd.springweb1209.services;

import com.uospd.springweb1209.entities.Authority;
import com.uospd.springweb1209.entities.User;
import com.uospd.springweb1209.repositories.AuthorityRepository;
import com.uospd.springweb1209.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthorityRepository authorityRepository;

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

    public boolean emailRegistered(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public void saveUser(User user){
        if(user.getEmail() == null || !user.getEmail().contains("@")) return;
        if(userExist(user.getUsername()) || emailRegistered(user.getEmail())) return;
        user.setEnabled(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Authority roleUser = new Authority("ROLE_USER");
        user.addAuthority(roleUser);
        roleUser.setUser(user);
        authorityRepository.save(roleUser);

    }
}