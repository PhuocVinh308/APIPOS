package com.example.demo.repository;


import com.example.demo.model.Attendance;
import com.example.demo.model.Employee;
import com.example.demo.model.ShiftSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEmployeeAndDate(Employee employee, Date date);

    Attendance findByEmployeeAndShiftSchedule(Employee employee, ShiftSchedule shiftSchedule);

    List<Attendance> findByEmployeeAndDateBetween(Employee employee, Date startDate, Date endDate);
}
