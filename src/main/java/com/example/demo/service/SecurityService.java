package com.example.demo.service;

import com.example.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final EmployeeService employeeService;

    public SecurityService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public String getLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public boolean isUserManager() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_MANAGER"::equals);
    }
    
    public Employee getLoggedEmployee() {
        String username = getLoggedInUsername();
        return employeeService.findByUsername(username);
    }
}