package com.example.demo.repository;

import com.example.demo.model.Employee;
import com.example.demo.model.ShiftSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShiftScheduleRepository extends JpaRepository<ShiftSchedule, Long> {
}
