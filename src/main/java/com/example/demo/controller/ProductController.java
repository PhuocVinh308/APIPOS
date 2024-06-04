package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import okhttp3.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private String keyRmbg ="9WsAdrCS37J4oPANt7qRS3MR";
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/do-an")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<Product>> getDoAn() {
        List<Product> products = productService.getDoAn();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/nuoc")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<Product>> getNuoc() {
        List<Product> products = productService.getNuoc();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/max")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public Long getIDMax() {
        return productService.getMaxId();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Product> saveOrUpdateProduct(@RequestBody Product product) {

        String tenFile = String.valueOf(System.currentTimeMillis());

        OkHttpClient client = new OkHttpClient();

        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image_url", product.getLinkImage())
                .addFormDataPart("size", "auto")
                .build();

        Request request = new Request.Builder()
                .url("https://api.remove.bg/v1.0/removebg")
                .header("X-Api-Key", keyRmbg)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            File imagesDir = new File(System.getProperty("user.dir") + File.separator + "images");
            imagesDir.mkdirs();
            String filePath = imagesDir.getAbsolutePath() + File.separator + tenFile + ".png";
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            product.setLinkLocal(filePath);
            fos.write(response.body().bytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product savedProduct = productService.saveOrUpdateProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteProductById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.getProductById(id);
        productService.deleteProductById(id);

//        if (productOptional.isPresent()) {
//            Product product = productOptional.get();
//            if (product.getLinkLocal() != null && !product.getLinkLocal().isEmpty()) {
//                File imageFile = new File(product.getLinkLocal());
//                if (imageFile.exists()) {
//                    if (imageFile.delete()) {
//                        System.out.println("Deleted the file: " + imageFile.getName());
//                    } else {
//                        System.out.println("Failed to delete the file: " + imageFile.getName());
//                    }
//                }
//            }
//        productService.deleteProductById(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }



    @GetMapping("/revert/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void revertProduct(@PathVariable Long id) {
         productService.revertProduct(id);
}
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productService.getProductById(id)
                .map(existingProduct -> {
                    existingProduct.setProductName(updatedProduct.getProductName());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    existingProduct.setLinkImage(updatedProduct.getLinkImage());
                    String tenFile = String.valueOf(System.currentTimeMillis());
                    File imagesDir = new File(System.getProperty("user.dir") + File.separator + "images");
                    imagesDir.mkdirs();
                    String destinationPath = imagesDir.getAbsolutePath() + File.separator + tenFile + ".png";

                    OkHttpClient client = new OkHttpClient();
                    MultipartBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("image_url", updatedProduct.getLinkImage())
                            .addFormDataPart("size", "auto")
                            .build();

                    Request request = new Request.Builder()
                            .url("https://api.remove.bg/v1.0/removebg")
                            .header("X-Api-Key", keyRmbg)
                            .post(requestBody)
                            .build();

                    try (Response response = client.newCall(request).execute()) {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                        FileOutputStream fos = new FileOutputStream(new File(existingProduct.getLinkLocal()));
                        fos.write(response.body().bytes());
                        fos.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    productService.saveOrUpdateProduct(existingProduct);
                    return new ResponseEntity<>(existingProduct, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
