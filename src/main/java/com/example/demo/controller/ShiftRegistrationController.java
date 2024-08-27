package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.model.Shift;
import com.example.demo.model.ShiftRegistration;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.ShiftRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shift-registrations")
public class ShiftRegistrationController {

    @Autowired
    private ShiftRegistrationService shiftRegistrationService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public String registerForShift(@RequestBody ShiftRegistration shiftRegistration) {
       return shiftRegistrationService.registerForShift(shiftRegistration);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<ShiftRegistration>> getAllShiftRegistrations() {
        List<ShiftRegistration> shiftRegistrations = shiftRegistrationService.getAllShiftRegistrations();
        return new ResponseEntity<>(shiftRegistrations, HttpStatus.OK);
    }
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public List<Map<String,Object>> getRegisteredShifts(@PathVariable Long employeeId) {
        return shiftRegistrationService.getRegisteredShiftsForEmployee(employeeId);
    }
    @GetMapping("/task/{employeeId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public List<Map<String,Object>> getTask(@PathVariable Long employeeId) {
        return shiftRegistrationService.getTask(employeeId);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<ShiftRegistration> getShiftRegistrationById(@PathVariable Long id) {
        ShiftRegistration shiftRegistration = shiftRegistrationService.getShiftRegistrationById(id);
        return new ResponseEntity<>(shiftRegistration, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Void> deleteShiftRegistration(@PathVariable Long id) {
        shiftRegistrationService.deleteShiftRegistration(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
