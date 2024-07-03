package com.example.demo.repository;

import com.example.demo.model.Employee;
import com.example.demo.model.ShiftRegistration;
import com.example.demo.model.ShiftSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRegistrationRepository extends JpaRepository<ShiftRegistration, Long> {
    boolean existsByShiftScheduleAndEmployee(ShiftSchedule shiftSchedule, Employee employee);
}
