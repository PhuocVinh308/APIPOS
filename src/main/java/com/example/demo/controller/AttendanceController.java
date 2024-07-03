package com.example.demo.controller;


import com.example.demo.model.Attendance;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.SalaryCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public AttendanceController(AttendanceService attendanceService,
                                EmployeeRepository employeeRepository ) {
        this.attendanceService = attendanceService;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Long id) {
        Attendance attendance = attendanceService.getAttendanceById(id);
        return new ResponseEntity<>(attendance, HttpStatus.OK);
    }

    @PostMapping("/check-in")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> checkIn(@RequestParam Long employeeId, @RequestParam Long shiftScheduleId) {
     String mess = attendanceService.checkIn(employeeId, shiftScheduleId);
        return new ResponseEntity<>(mess, HttpStatus.OK);
    }

    @PostMapping("/check-out")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> checkOut(@RequestParam Long employeeId, @RequestParam Long shiftScheduleId) {
        attendanceService.checkOut(employeeId, shiftScheduleId);
        return new ResponseEntity<>("Điểm danh ra thành công.", HttpStatus.OK);
    }




}
