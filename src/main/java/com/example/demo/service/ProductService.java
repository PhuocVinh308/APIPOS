package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        System.out.print("Da xuat");
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
    public void saveImageFromUrl(String imageUrl, String destinationPath) throws IOException {
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new IllegalArgumentException("Đường dẫn hình ảnh là null hoặc trống");
        }


        Path directory = Paths.get(destinationPath).getParent();
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }

        URL url = new URL(imageUrl);
        try (InputStream in = url.openStream()) {
            Path destination = Paths.get(destinationPath);
            Files.copy(in, destination);

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Long getMaxId() {
        return productRepository.getMaxID();
    }
}
