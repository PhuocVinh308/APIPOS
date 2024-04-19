package com.example.demo;

import com.example.demo.model.UserInfo;
import com.example.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!userInfoService.existsByUsername("admin")) {
            UserInfo adminUser = new UserInfo();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin");
            adminUser.setRoles("ROLE_ADMIN");
            userInfoService.addUser(adminUser);
        }
    }
}
