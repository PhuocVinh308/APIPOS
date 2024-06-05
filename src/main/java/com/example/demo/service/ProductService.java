package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Cacheable(value ="products")
    public List<Product> getAllProducts() {
        return productRepository.findProduct();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @CacheEvict(value = "products", allEntries = true)
    public Product saveOrUpdateProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @CacheEvict(value = "products", allEntries = true)
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void revertProduct(Long id) {
        productRepository.revertProductByID(id);
    }

    public Long getMaxId() {
        return productRepository.getMaxID();
    }


    @Cacheable(value ="products")
    public List<Product> getNuoc() {
        return productRepository.getNuoc();
    }

    public List<Product> getDoAn() {
        return productRepository.getDoAn();
    }


    @Cacheable(value ="products")
    public List<Product> getNuocDelete() {
        return productRepository.getNuocDelete();
    }

    public List<Product> getThucAnDelete() {
        return productRepository.getThucAnDelete();
    }
}
