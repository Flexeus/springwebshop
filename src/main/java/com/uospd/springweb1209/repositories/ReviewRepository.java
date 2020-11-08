package com.uospd.springweb1209.repositories;

import com.uospd.springweb1209.entities.Product;
import com.uospd.springweb1209.entities.Review;
import com.uospd.springweb1209.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> getAllByProduct(Product product, Pageable pageable);
    Review getByProductAndAuthor(Product product, User user);
}
