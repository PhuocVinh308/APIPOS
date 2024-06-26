package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.codeDM like 'NUOC_UONG' and p.is_delete = false ")
    List<Product> getNuoc();

    @Modifying
    @Query(value = "UPDATE product SET is_delete = 1 WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE product SET is_delete = 0 WHERE id = :id", nativeQuery = true)
    void revertProductByID(@Param("id") Long id);

@Query(value = "select * from product p where p.is_delete = 0",nativeQuery = true)
    List<Product> findProduct();
    @Query(value = "Select max(p.id) from Product p",nativeQuery = true)
    Long getMaxID();

    @Query("SELECT p FROM Product p WHERE p.codeDM like 'DO_AN' and p.is_delete = false ")
    List<Product> getDoAn();

    @Query(value="select * from product p where p.id = :id and p.is_delete = false",nativeQuery = true)
    Product getProduct(Long id);

    @Query("SELECT p FROM Product p WHERE  p.is_delete = true ")
    List<Product> getNuocDelete();

    @Query("SELECT p FROM Product p WHERE p.codeDM like 'THUC_AN' and p.is_delete = true ")
    List<Product> getThucAnDelete();
}

