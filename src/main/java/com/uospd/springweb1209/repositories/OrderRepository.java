package com.uospd.springweb1209.repositories;

import com.uospd.springweb1209.entities.Order;
import com.uospd.springweb1209.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Page<Order> getAllByUser(User user, Pageable pageable);
    Page<Order> findAll(Pageable pageable);
}
