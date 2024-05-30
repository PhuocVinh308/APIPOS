package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByAccount(String username);

    @Modifying
    @Query(value = "Update Employee set isDeleted = true where employeeId =:id")
    void DeleteNhanVien(Long id);
}
