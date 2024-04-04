package com.example.demo.repository;

import com.example.demo.model.Ban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  BanRepository extends JpaRepository<Ban, Long> {
    Optional<Ban> findByStatus(boolean status);


    @Query(value = "DELETE FROM Order_item WHERE order_id IN (SELECT id FROM Orders WHERE ban_id = :banId)\n" +
            "DELETE FROM ban WHERE id = :banId\n",nativeQuery = true)
    @Modifying
    void deleteBanById(Long banId);
}


