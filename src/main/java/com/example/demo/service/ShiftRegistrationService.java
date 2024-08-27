package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.model.ShiftRegistration;
import com.example.demo.model.ShiftSchedule;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ShiftRegistrationRepository;
import com.example.demo.repository.ShiftScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ShiftRegistrationService {

    private final ShiftRegistrationRepository shiftRegistrationRepository;
    private final EmployeeRepository employeeRepository;
    private final ShiftScheduleRepository shiftScheduleRepository;

    @Autowired
    public ShiftRegistrationService(ShiftRegistrationRepository shiftRegistrationRepository,
                                    EmployeeRepository employeeRepository,
                                    ShiftScheduleRepository shiftScheduleRepository) {
        this.shiftRegistrationRepository = shiftRegistrationRepository;
        this.employeeRepository = employeeRepository;
        this.shiftScheduleRepository = shiftScheduleRepository;
    }

    public String registerForShift(ShiftRegistration shiftRegistration) {
        Optional<ShiftSchedule> optionalShiftSchedule = shiftScheduleRepository.findById(shiftRegistration.getShiftSchedule().getId());
        Employee emp = employeeRepository.findByEmployeeId(shiftRegistration.getEmployee().getEmployeeId());
        if (optionalShiftSchedule.isEmpty()) {
            return "Không tìm thấy ca làm việc.";
        }

        ShiftSchedule shiftSchedule = optionalShiftSchedule.get();
        Employee employee = shiftRegistration.getEmployee();

        boolean isRegistered = shiftRegistrationRepository.existsByShiftScheduleAndEmployee(shiftSchedule, employee);
        if (isRegistered) {
            return "Nhân viên đã đăng ký cho ca này rồi.";
        }

        if (shiftSchedule.getCapacity() > 0) {
            shiftSchedule.setCapacity(shiftSchedule.getCapacity() - 1);
            shiftScheduleRepository.save(shiftSchedule);
             shiftRegistrationRepository.save(shiftRegistration);
             return " Nhân viên: " +emp.getFullName() + "\nĐăng ký ca thành công.";
        } else {
            return ("Ca đăng ký đã đầy.");
        }
    }



    public List<ShiftRegistration> getAllShiftRegistrations() {
        return shiftRegistrationRepository.findAll();
    }

    public ShiftRegistration getShiftRegistrationById(Long id) {
        return shiftRegistrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift Registration not found"));
    }

    public void deleteShiftRegistration(Long id) {
        ShiftRegistration shiftRegistration = shiftRegistrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift Registration not found"));
        Long idShift = shiftRegistration.getShiftSchedule().getId();
        Optional<ShiftSchedule> temp = shiftScheduleRepository.findById(idShift);
        if (temp.isPresent()) {
            ShiftSchedule shiftSchedule = temp.get();
            shiftSchedule.setCapacity(shiftSchedule.getCapacity()+1);
            shiftScheduleRepository.save(shiftSchedule);
        }
        shiftRegistrationRepository.delete(shiftRegistration);

    }

    public List<Map<String,Object>> getRegisteredShiftsForEmployee(Long employeeId) {
        return shiftRegistrationRepository.findByEmployeeId(employeeId);
    }

    public List<Map<String, Object>> getTask(Long employeeId) {
        return shiftRegistrationRepository.getScheduleByEmployeeId(employeeId);
    }
}
