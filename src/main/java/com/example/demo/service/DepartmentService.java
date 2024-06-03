package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public Department updateDepartment(Long id, Department newDepartment) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setNameDepartment(newDepartment.getNameDepartment());
                    department.setFullAddress(newDepartment.getFullAddress());
                    department.setManager(newDepartment.getManager());
                    department.setLongitude(newDepartment.getLongitude());
                    department.setLatitude(newDepartment.getLatitude());
                    return departmentRepository.save(department);
                })
                .orElseThrow(() -> new RuntimeException("Department not found with id " + id));
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
