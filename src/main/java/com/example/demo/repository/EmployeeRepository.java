package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByAccount(String username);
    Employee findByEmployeeId(Long id);
    @Modifying
    @Query(value = "Update Employee set isDeleted = true where employeeId =:id")
    void DeleteNhanVien(Long id);


    @Query(value = "SELECT\n" +
            "  e.id AS employee_id,\n" +
            "  e.full_name AS employee_name,\n" +
            "  e.salary AS salary_per_hour,\n" +
            "  SUM(\n" +
            "    CASE WHEN (a.check_in IS NOT NULL AND a.check_out IS NOT NULL) THEN\n" +
            "      TIMESTAMPDIFF(MINUTE,  s.startTime, s.endTime) / 60.0\n" +
            "    ELSE\n" +
            "      0\n" +
            "    END\n" +
            "  ) AS total_worked_hours,\n" +
            "  SUM(o.total_amount) * 0.02 AS sales_bonus,\n" +
            "  SUM(\n" +
            "    CASE WHEN (a.check_in IS NOT NULL AND a.check_out IS NOT NULL) THEN\n" +
            "      TIMESTAMPDIFF(MINUTE, a.check_in, a.check_out) / 60.0 * e.salary\n" +
            "    ELSE\n" +
            "      0\n" +
            "    END\n" +
            "  ) AS total_salary\n" +
            "FROM employee e\n" +
            "JOIN attendance a ON e.id = a.employee_id\n" +
            "JOIN shift_schedule s ON s.id = a.shift_schedule_id\n" +
            "JOIN shift_registration sr ON sr.employee_id = e.id AND sr.shift_schedule_id = s.id\n" +
            "JOIN orders o ON o.employee_id = e.id AND o.order_date >= 2024/1/1 AND o.order_date <= 2024/12/31\n" +
            "WHERE a.date >= 2024/1/1 AND a.date <= 2024/12/31\n" +
            "GROUP BY e.id, e.full_name, e.salary;",nativeQuery = true)
    Map<String,Object> tinhLuong();
}
