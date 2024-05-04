package com.example.demo.controller;

import com.example.demo.model.Shift;
import com.example.demo.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<Shift>> getAllShifts() {
        List<Shift> shifts = shiftService.getAllShifts();
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Shift> getShiftById(@PathVariable Long id) {
        return shiftService.getShiftById(id)
                .map(shift -> new ResponseEntity<>(shift, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Shift> createShift(@RequestBody Shift shift) {
        Shift createdShift = shiftService.createShift(shift);
        return new ResponseEntity<>(createdShift, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Shift> updateShift(@PathVariable Long id, @RequestBody Shift shift) {
        Shift updatedShift = shiftService.updateShift(id, shift);
        if (updatedShift != null) {
            return new ResponseEntity<>(updatedShift, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Void> deleteShift(@PathVariable Long id) {
        shiftService.deleteShift(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
