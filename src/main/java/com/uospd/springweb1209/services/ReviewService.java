package com.uospd.springweb1209.services;

import com.uospd.springweb1209.entities.Product;
import com.uospd.springweb1209.entities.Review;
import com.uospd.springweb1209.entities.User;
import com.uospd.springweb1209.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    public Page<Review> getProductReviews(Product product, Pageable pageable){
        return reviewRepository.getAllByProduct(product,pageable);
    }


    public void createReview(User user,Long productId,Review review){
        Product product = productService.getProductByID(productId);
        review.setProduct(product);
        if(userDidPreview(user,review.getProduct()) || review.getText() == null) return;
        review.setDate(new Date(System.currentTimeMillis()));
        review.setAuthor(user);
        reviewRepository.save(review);
    }

    public boolean userDidPreview(User user, Product product){
        if(user == null || product == null) return false;
        return reviewRepository.getByProductAndAuthor(product, user).isPresent();
    }

    public void saveReview(Review review){
        System.out.println("TRYIN TO SAVE");
        reviewRepository.save(review);
    }
}
