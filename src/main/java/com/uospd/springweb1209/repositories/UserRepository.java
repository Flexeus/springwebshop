package com.uospd.springweb1209.repositories;

import com.uospd.springweb1209.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
}
