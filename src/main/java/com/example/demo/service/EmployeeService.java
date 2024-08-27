package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll().stream().filter(employee -> !employee.getAccount().equals("admin") && !employee.isDeleted()).collect(Collectors.toList());
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee e = employeeRepository.findByEmployeeId(id);
        e.setDeleted(true);
        employeeRepository.save(e);

    }

    public Employee getEmployeeByUsername(String username) {
        return employeeRepository.findByAccount(username);
    }

    public String getImageProfileById(Long id) throws Exception {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if (employee.getImageProfile() != null) {
                return employee.getImageProfile();
            } else {
                throw new Exception("Image not found");
            }
        } else {
            throw new Exception("Employee not found");
        }
    }
}
