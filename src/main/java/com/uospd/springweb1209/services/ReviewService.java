package com.uospd.springweb1209.services;

import com.uospd.springweb1209.entities.Product;
import com.uospd.springweb1209.entities.Review;
import com.uospd.springweb1209.entities.User;
import com.uospd.springweb1209.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserService userService;

    public Page<Review> getProductReviews(Product product, Pageable pageable){
        return reviewRepository.getAllByProduct(product,pageable);
    }

    public void createReview(Product product,User author,String reviewText,Integer rating){
        if(userDidPreview(author,product)) return;
        Review review = new Review(author,reviewText);
        if(rating != null) review.setRating(rating);
        review.setProduct(product);
        reviewRepository.save(review);
    }

    public boolean userDidPreview(User user, Product product){
        if(user == null || product == null) return false;
        Review review = reviewRepository.getByProductAndAuthor(product, user);
        return review != null;
    }

    public void saveReview(Review review){
        System.out.println("TRYIN TO SAVE");
        reviewRepository.save(review);
    }
}
