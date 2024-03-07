package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://10.12.44.29:3000") //Chi nh Cros cho IP FE
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @PostMapping()
    public ResponseEntity<Product> saveOrUpdateProduct(@RequestBody Product product) {
        if (product.getLinkImage() != null && !product.getLinkImage().isEmpty()) {
            try {
                String destinationPath = System.getProperty("user.dir") + File.separator + "images" + File.separator + product.getProductName() + ".jpg";
                productService.saveImageFromUrl(product.getLinkImage(), destinationPath);
                product.setLinkLocal(destinationPath);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // Lưu hoặc cập nhật sản phẩm vào cơ sở dữ liệu
        Product savedProduct = productService.saveOrUpdateProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productService.getProductById(id)
                .map(existingProduct -> {
                    existingProduct.setProductName(updatedProduct.getProductName());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    existingProduct.setLinkImage(updatedProduct.getLinkImage());
                    Product savedProduct = productService.saveOrUpdateProduct(existingProduct);

                    return new ResponseEntity<>(savedProduct, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
}

