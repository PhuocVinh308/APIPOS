package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.model.ShiftSchedule;
import com.example.demo.model.ShiftRegistration;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ShiftRegistrationRepository;
import com.example.demo.repository.ShiftScheduleRepository;
import org.apache.poi.ss.formula.functions.Now;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShiftScheduleService {

    private final ShiftScheduleRepository shiftScheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final ShiftRegistrationRepository shiftRegistrationRepository;

    @Autowired
    public ShiftScheduleService(ShiftScheduleRepository shiftScheduleRepository,
                                EmployeeRepository employeeRepository,
                                ShiftRegistrationRepository shiftRegistrationRepository) {
        this.shiftScheduleRepository = shiftScheduleRepository;
        this.employeeRepository = employeeRepository;
        this.shiftRegistrationRepository = shiftRegistrationRepository;
    }

    public ShiftSchedule createShiftSchedule(ShiftSchedule shiftSchedule) {
        return shiftScheduleRepository.save(shiftSchedule);
    }

    public List<ShiftSchedule> getAllShiftSchedules() {
        Date now = new Date();
        return shiftScheduleRepository.findAll().stream()
                .filter(schedule -> schedule.getCapacity() > 0 && schedule.getScheduleDate().after(now))
                .collect(Collectors.toList());
    }

    public ShiftSchedule getShiftScheduleById(Long id) {
        return shiftScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift Schedule not found"));
    }

    public ShiftSchedule updateShiftSchedule(Long id, ShiftSchedule shiftScheduleDetails) {
        ShiftSchedule shiftSchedule = shiftScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift Schedule not found"));

        shiftSchedule.setName(shiftScheduleDetails.getName());
        shiftSchedule.setStartTime(shiftScheduleDetails.getStartTime());
        shiftSchedule.setEndTime(shiftScheduleDetails.getEndTime());
        shiftSchedule.setScheduleDate(shiftSchedule.getScheduleDate());
        shiftSchedule.setCapacity(shiftScheduleDetails.getCapacity());

        return shiftScheduleRepository.save(shiftSchedule);
    }

    public void deleteShiftSchedule(Long id) {
        ShiftSchedule shiftSchedule = shiftScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift Schedule not found"));

        shiftScheduleRepository.delete(shiftSchedule);
    }

    public void registerForShift(Long shiftScheduleId, Long employeeId) {
        ShiftSchedule shiftSchedule = shiftScheduleRepository.findById(shiftScheduleId)
                .orElseThrow(() -> new RuntimeException("Shift Schedule not found"));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));



        ShiftRegistration shiftRegistration = new ShiftRegistration();
        shiftRegistration.setEmployee(employee);
        shiftRegistration.setShiftSchedule(shiftSchedule);

        shiftRegistrationRepository.save(shiftRegistration);
    }
}
