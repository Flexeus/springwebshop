package com.uospd.springweb1209.services;

import com.uospd.springweb1209.entities.Product;
import com.uospd.springweb1209.entities.Review;
import com.uospd.springweb1209.entities.User;
import com.uospd.springweb1209.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    public Page<Review> getProductReviews(Product product, Pageable pageable){
        return reviewRepository.getAllByProduct(product,pageable);
    }

    public void createReview(User user,Long productId,Review review){
        if(review.getText() == null || review.getText().isEmpty()) return;
        Product product = productService.getProductByID(productId);
        if(product == null || user == null) return;
        review.setProduct(product);
        if(userDidPreview(user,review.getProduct())) return;
        review.setAuthor(user);
        reviewRepository.save(review);
    }

    public boolean userDidPreview(User user, Product product){
        if(user == null || product == null) return false;
        return reviewRepository.getByProductAndAuthor(product, user).isPresent();
    }

    public void saveReview(Review review){
        reviewRepository.save(review);
    }
}
