package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveOrUpdateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public void saveImageFromUrl(String imageUrl, String destinationPath) throws IOException {
        // Tạo đường dẫn đến thư mục lưu trữ hình ảnh
        Path directory = Paths.get(destinationPath).getParent();
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                // Xử lý ngoại lệ khi không thể tạo thư mục
                e.printStackTrace();
                throw e;
            }
        }

        // Tải hình ảnh từ URL và lưu vào ổ đĩa
        URL url = new URL(imageUrl);
        try (InputStream in = url.openStream()) {
            Path destination = Paths.get(destinationPath);
            Files.copy(in, destination);
        } catch (IOException e) {
            // Xử lý ngoại lệ khi không thể tải hình ảnh
            e.printStackTrace();
            throw e;
        }
    }
}
