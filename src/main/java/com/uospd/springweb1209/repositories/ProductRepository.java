package com.uospd.springweb1209.repositories;


import com.uospd.springweb1209.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);


    @Modifying
    @Query(value = "delete Product where id = :id",nativeQuery = false)
    void deleteById(Long id);
}

