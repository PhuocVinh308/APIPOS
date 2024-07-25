package com.example.demo.repository;

import com.example.demo.model.Employee;
import com.example.demo.model.ShiftRegistration;
import com.example.demo.model.ShiftSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShiftRegistrationRepository extends JpaRepository<ShiftRegistration, Long> {
    boolean existsByShiftScheduleAndEmployee(ShiftSchedule shiftSchedule, Employee employee);

    @Query(value = "SELECT \n" +
            "    ss.*,sr.id as idDangKy,\n" +
            "    CASE \n" +
            "        WHEN sr.employee_id = :employeeId THEN true\n" +
            "        ELSE false\n" +
            "    END AS is_registered\n" +
            "FROM \n" +
            "    webnl.shift_schedule ss\n" +
            "LEFT JOIN \n" +
            "    webnl.shift_registration sr \n" +
            "ON \n" +
            "    ss.id = sr.shift_schedule_id \n" +
            "    AND sr.employee_id = :employeeId\n" +
            "WHERE \n" +
            "    ss.scheduleDate >= CURRENT_DATE;\n", nativeQuery = true)
    List<Map<String,Object>> findByEmployeeId(Long employeeId);

    @Query(value = "SELECT \n" +
            "    ss.*,sr.id as idDangKy\n" +
            "FROM \n" +
            "    shift_schedule ss\n" +
            "LEFT JOIN \n" +
            "    shift_registration sr \n" +
            "ON \n" +
            "    ss.id = sr.shift_schedule_id \n" +
            "WHERE \n" +
            "    ss.scheduleDate >= CURRENT_DATE " +
            " AND sr.employee_id = :employeeId "+
            "ORDER BY ss.scheduleDate ASC, ss.startTime ASC", nativeQuery = true)
    List<Map<String,Object>> getScheduleByEmployeeId(Long employeeId);

}
