package com.example.demo.service;

import com.example.demo.model.Shift;
import com.example.demo.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    // Phương thức để lấy tất cả các ca làm
    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    // Phương thức để lấy một ca làm bằng ID
    public Optional<Shift> getShiftById(Long id) {
        return shiftRepository.findById(id);
    }

    // Phương thức để tạo mới một ca làm
    public Shift createShift(Shift shift) {
        return shiftRepository.save(shift);
    }

    // Phương thức để cập nhật thông tin của một ca làm
    public Shift updateShift(Long id, Shift newShift) {
        Optional<Shift> optionalShift = shiftRepository.findById(id);
        if (optionalShift.isPresent()) {
            Shift existingShift = optionalShift.get();
            existingShift.setStartTime(newShift.getStartTime());
            existingShift.setEndTime(newShift.getEndTime());
            existingShift.setQuantity(newShift.getQuantity());
            return shiftRepository.save(existingShift);
        } else {
            return null; // hoặc throw một exception tùy thuộc vào yêu cầu
        }
    }

    // Phương thức để xóa một ca làm bằng ID
    public void deleteShift(Long id) {
        shiftRepository.deleteById(id);
    }
}
