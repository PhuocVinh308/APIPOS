package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.model.ShiftRegistration;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.ShiftRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shift-registrations")
public class ShiftRegistrationController {

    @Autowired
    private ShiftRegistrationService shiftRegistrationService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> registerForShift(@RequestBody ShiftRegistration shiftRegistration) {
        String registeredShiftRegistration = shiftRegistrationService.registerForShift(shiftRegistration);
        return new ResponseEntity<String>(registeredShiftRegistration, HttpStatus.OK);

    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<ShiftRegistration>> getAllShiftRegistrations() {
        List<ShiftRegistration> shiftRegistrations = shiftRegistrationService.getAllShiftRegistrations();
        return new ResponseEntity<>(shiftRegistrations, HttpStatus.OK);
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
