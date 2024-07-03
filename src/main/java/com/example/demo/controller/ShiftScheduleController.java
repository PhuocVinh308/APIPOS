package com.example.demo.controller;

import com.example.demo.model.ShiftSchedule;
import com.example.demo.service.ShiftScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shift-schedules")
public class ShiftScheduleController {

    @Autowired
    private ShiftScheduleService shiftScheduleService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<ShiftSchedule> createShiftSchedule(@RequestBody ShiftSchedule shiftSchedule) {
        ShiftSchedule createdShiftSchedule = shiftScheduleService.createShiftSchedule(shiftSchedule);
        return new ResponseEntity<>(createdShiftSchedule, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<ShiftSchedule>> getAllShiftSchedules() {
        List<ShiftSchedule> shiftSchedules = shiftScheduleService.getAllShiftSchedules();
        return new ResponseEntity<>(shiftSchedules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<ShiftSchedule> getShiftScheduleById(@PathVariable Long id) {
        ShiftSchedule shiftSchedule = shiftScheduleService.getShiftScheduleById(id);
        return new ResponseEntity<>(shiftSchedule, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<ShiftSchedule> updateShiftSchedule(@PathVariable Long id, @RequestBody ShiftSchedule shiftScheduleDetails) {
        ShiftSchedule updatedShiftSchedule = shiftScheduleService.updateShiftSchedule(id, shiftScheduleDetails);
        return new ResponseEntity<>(updatedShiftSchedule, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteShiftSchedule(@PathVariable Long id) {
        shiftScheduleService.deleteShiftSchedule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/register")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> registerForShift(@PathVariable Long id, @RequestParam Long employeeId) {
        shiftScheduleService.registerForShift(id, employeeId);
        return new ResponseEntity<>("Đăng ký ca thành công.", HttpStatus.OK);
    }
}
