package com.example.demo.repository;

import com.example.demo.model.Ban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  BanRepository extends JpaRepository<Ban, Long> {
    Optional<Ban> findByStatus(boolean status);


    @Query(value = "select * from ban b where b.is_deleted = 0",nativeQuery = true)
    List<Ban> findBan();

    @Modifying
    @Query(value = "UPDATE ban SET is_deleted = 1 WHERE id = :banId",nativeQuery = true)
    void deleteBanById(Long banId);
}


