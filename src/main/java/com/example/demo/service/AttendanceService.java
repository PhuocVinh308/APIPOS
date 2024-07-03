package com.example.demo.service;


import com.example.demo.model.Attendance;
import com.example.demo.model.Employee;
import com.example.demo.model.ShiftRegistration;
import com.example.demo.model.ShiftSchedule;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ShiftRegistrationRepository;
import com.example.demo.repository.ShiftScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final ShiftScheduleRepository shiftScheduleRepository;
    private final ShiftRegistrationRepository shiftRegistrationRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository,
                             EmployeeRepository employeeRepository,
                             ShiftScheduleRepository shiftScheduleRepository,
                             ShiftRegistrationRepository shiftRegistrationRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
        this.shiftScheduleRepository = shiftScheduleRepository;
        this.shiftRegistrationRepository = shiftRegistrationRepository;
    }

    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chấm công với id = " + id));
    }

    public Attendance createOrUpdateAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    public List<Attendance> getAttendanceByEmployeeAndDate(Employee employee, Date date) {
        return attendanceRepository.findByEmployeeAndDate(employee, date);
    }

    public String checkIn(Long employeeId, Long shiftScheduleId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        ShiftSchedule shiftSchedule = shiftScheduleRepository.findById(shiftScheduleId)
                .orElseThrow(() -> new RuntimeException("Shift schedule not found"));
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime shiftStartTime = shiftSchedule.getStartTime().toLocalTime();
        LocalTime allowedCheckInTime = shiftStartTime.minusMinutes(30);
        LocalTime deadlineCheckInTime = shiftStartTime.plusMinutes(10);
        LocalTime checkInTime = currentDateTime.toLocalTime();
        if (checkInTime.isBefore(allowedCheckInTime) || checkInTime.isAfter(deadlineCheckInTime)) {
            return "Không thể điểm danh vào sau thời gian cho phép.";
        }
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(new Date());
        attendance.setCheckIn(new Date());
        attendance.setStatus("Đã điểm danh vào");
        attendance.setShiftSchedule(shiftSchedule);
        attendanceRepository.save(attendance);
        return "Điểm danh vào thành công " + employee.getFullName();
    }

    public String checkOut(Long employeeId, Long shiftScheduleId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        ShiftSchedule shiftSchedule = shiftScheduleRepository.findById(shiftScheduleId)
                .orElseThrow(() -> new RuntimeException("Shift schedule not found"));
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime shiftEndTime = shiftSchedule.getEndTime().toLocalTime();
        LocalTime allowedCheckOutTime = shiftEndTime.plusMinutes(30);
        LocalTime checkOutTime = currentDateTime.toLocalTime();
        if (checkOutTime.isAfter(allowedCheckOutTime) || checkOutTime.isBefore(shiftEndTime)) {
             return ("Không thể điểm danh ra sau thời gian cho phép.");
        }
        Attendance attendance = attendanceRepository.findByEmployeeAndShiftSchedule(employee, shiftSchedule);
        attendance.setCheckOut(new Date());
        if ( attendance.getCheckIn() != null )
        attendance.setStatus("Điểm danh hop lệ!");
        else {
            attendance.setStatus("Quen diem danh vao!");
        }
        attendanceRepository.save(attendance);
        return "Điểm danh ra thành công " + employee.getFullName();
    }

}