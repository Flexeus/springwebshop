package com.uospd.springweb1209.services;

import com.uospd.springweb1209.entities.Category;
import com.uospd.springweb1209.entities.Product;
import com.uospd.springweb1209.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> getAllProductsPage(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public Product getProductByID(Long id) {
        return productRepository.findById(id).orElse(null);
    }


    public Page<Product> findProductsByTitle(String title,Pageable pageable) {
        return productRepository.findAllByTitleContainingIgnoreCase(title,pageable);
    }

    public void deleteProductByID(Long id) {
        productRepository.deleteById(id);
    }

    public void saveOrUpdateProduct(Product product, MultipartFile imageFile) {
        try {
            if(imageFile.isEmpty()) return;
            product.setImage(imageFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        productRepository.save(product);
    }

    public Page<Product> getAllProductsByCategoryId(Long categoryId,Pageable pageable){
        return productRepository.findAllByCategoryId(categoryId,pageable);
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

}
