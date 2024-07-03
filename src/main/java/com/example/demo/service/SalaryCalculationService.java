package com.example.demo.service;

import com.example.demo.model.Attendance;
import com.example.demo.model.Employee;
import com.example.demo.model.Order;
import com.example.demo.model.ShiftSchedule;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShiftScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SalaryCalculationService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final ShiftScheduleRepository shiftScheduleRepository;

    private final OrderRepository orderRepository;

    public SalaryCalculationService(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository,
                                    ShiftScheduleRepository shiftScheduleRepository, OrderRepository orderRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
        this.shiftScheduleRepository = shiftScheduleRepository;
        this.orderRepository = orderRepository;
    }

    public double calculateSalaryForCurrentMonth(Long employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

// Thiết lập ngày cuối cùng của tháng trước
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23); // Đặt giờ là 23 (11 PM)
        calendar.set(Calendar.MINUTE, 59); // Đặt phút là 59
        Date endDate = calendar.getTime();

// Thiết lập ngày đầu tiên của tháng trước
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        Date startDate = calendar.getTime();



        double basicSalary = calculateBasicSalary(employee, startDate, endDate);

        double bonusFromRevenue = calculateBonusFromRevenue(employee, startDate, endDate);

        double totalSalary = basicSalary + bonusFromRevenue;

        return totalSalary;
    }

    private double calculateBasicSalary(Employee employee, Date startDate, Date endDate) {
        List<Attendance> attendances = attendanceRepository.findByEmployeeAndDateBetween(employee, startDate, endDate);
        double totalHoursWorked = 0.0;
        for (Attendance attendance : attendances) {
            if (attendance.getCheckIn() != null && attendance.getCheckOut() != null) {
                long hoursWorked = (attendance.getCheckOut().getTime() - attendance.getCheckIn().getTime()) / (1000 * 60 * 60);
                totalHoursWorked += hoursWorked;
            }
        }
        int hourlyRate = employee.getSalary();

        double basicSalary = totalHoursWorked * hourlyRate;

        return basicSalary;
    }

    private double calculateBonusFromRevenue(Employee employee, Date startDate, Date endDate) {
        List<Attendance> attendances = attendanceRepository.findByEmployeeAndDateBetween(employee, startDate, endDate);
        List<ShiftSchedule> shiftSchedules = new ArrayList<>();

        for (Attendance attendance : attendances) {
            ShiftSchedule shiftSchedule = attendance.getShiftSchedule();
            if (shiftSchedule != null && !shiftSchedules.contains(shiftSchedule)) {
                shiftSchedules.add(shiftSchedule);
            }
        }

        double totalRevenue = 0.0;
        for (ShiftSchedule shiftSchedule : shiftSchedules) {
            double invoiceRevenue = calculateInvoiceRevenue(shiftSchedule);
            totalRevenue += invoiceRevenue;
        }
        return totalRevenue;
    }



    public double calculateInvoiceRevenue(ShiftSchedule shiftSchedule) {
        double totalRevenue = 0.0;
        List<Order> orders = orderRepository.findByOrderDateBetween(shiftSchedule.getStartDate(), shiftSchedule.getEndDate());
        for (Order order : orders) {
            totalRevenue += order.getTotalAmount() * 0.02; // 2%
        }
        return totalRevenue;
    }
}
