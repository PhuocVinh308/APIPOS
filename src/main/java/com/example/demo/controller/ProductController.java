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
import java.util.Optional;

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
                String destinationPath = System.getProperty("user.dir") + File.separator + "images" + File.separator + product.getId().toString() + ".jpg";
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
        Optional<Product> productOptional = productService.getProductById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getLinkLocal() != null && !product.getLinkLocal().isEmpty()) {
                File imageFile = new File(product.getLinkLocal());
                if (imageFile.exists()) {
                    if (imageFile.delete()) {
                        System.out.println("Deleted the file: " + imageFile.getName());
                    } else {
                        System.out.println("Failed to delete the file: " + imageFile.getName());
                    }
                }
            }

            // Delete the product from the database
            productService.deleteProductById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productService.getProductById(id)
                .map(existingProduct -> {
                    // Delete the existing image file if it exists
                    if (existingProduct.getLinkLocal() != null && !existingProduct.getLinkLocal().isEmpty()) {
                        File existingImageFile = new File(existingProduct.getLinkLocal());
                        if (existingImageFile.exists() && existingImageFile.delete()) {
                            System.out.println("Deleted the existing image file: " + existingImageFile.getName());
                        }
                    }
                    existingProduct.setProductName(updatedProduct.getProductName());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    existingProduct.setLinkImage(updatedProduct.getLinkImage());
                    try {
                        String destinationPath = System.getProperty("user.dir") + File.separator + "images" + File.separator + existingProduct.getProductName() + ".jpg";
                        File imageFile = new File(existingProduct.getLinkLocal());
                        if (imageFile.exists()) {
                            if (imageFile.delete()) {
                                System.out.println("Deleted the file: " + imageFile.getName());
                            } else {
                                System.out.println("Failed to delete the file: " + imageFile.getName());
                            }
                        }
                        productService.saveImageFromUrl(existingProduct.getLinkImage(), destinationPath);
                        existingProduct.setLinkLocal(destinationPath);
                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                    Product savedProduct = productService.saveOrUpdateProduct(existingProduct);
                    return new ResponseEntity<>(savedProduct, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



}

