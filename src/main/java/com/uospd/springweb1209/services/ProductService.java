package com.uospd.springweb1209.services;

import com.uospd.springweb1209.entities.Category;
import com.uospd.springweb1209.entities.Product;
import com.uospd.springweb1209.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {


    private ProductRepository productRepository;
    private CategoryService categoryService;

    public Page<Product> getAllProductsPage(Pageable pageable){
        Page<Product> products = productRepository.findAll(pageable);
        //products.sort(Comparator.comparingLong(Product::getId));
        return products;
    }

    public Product getProductByID(Long id) {
        return productRepository.findById(id).orElse(null);
    }


    public Page<Product> findProductsByTitle(String title,Pageable pageable) {
        return productRepository.findAllByTitleContainingIgnoreCase(title,pageable);
    }

   public void deleteProductByID(Long id){
        productRepository.deleteById(id);
    }

    public void addNewProduct(Product product, MultipartFile imageFile){
        try {
            product.setImage(imageFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        productRepository.save(product);
    }

    public Page<Product> getAllProductsByCategoryId(Long categoryId,Pageable pageable){
        return productRepository.findAllByCategoryId(categoryId,pageable);
    }

    public void updateProduct(Product product,MultipartFile imageFile){
        try {
            if(imageFile != null && imageFile.getBytes().length>50) product.setImage(imageFile.getBytes());
            else product.setImage(getProductByID(product.getId()).getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        productRepository.saveAndFlush(product);
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
