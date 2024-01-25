package com.example.demo.repository;

import com.example.demo.model.Ban;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanRepository extends JpaRepository<Ban, Long> {
    Optional<Ban> findByStatus(boolean status);
}


