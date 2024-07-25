package com.example.demo;

import com.example.demo.model.Employee;
import com.example.demo.model.UserInfo;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!userInfoService.existsByUsername("admin")) {
            UserInfo adminUser = new UserInfo();
            adminUser.setUsername("admin");
            adminUser.setPassword("123456");
            adminUser.setRoles("ROLE_ADMIN");
            userInfoService.addUser(adminUser);
            Employee emp = new Employee();
            emp.setEmployeeId(1L);
            emp.setFullName("Admin");
            emp.setAccount("admin");
            emp.setDeleted(false);
            employeeService.createEmployee(emp);
        }
    }
}
