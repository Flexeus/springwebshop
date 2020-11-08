package com.uospd.springweb1209.repositories;


import com.uospd.springweb1209.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAll(Pageable pageable);
    Product findOneByTitle(String title);
    Page<Product> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);
}
