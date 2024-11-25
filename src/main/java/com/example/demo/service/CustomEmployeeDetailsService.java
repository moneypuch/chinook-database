package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.model.CustomEmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.EmployeeRepository;

@Service
public class CustomEmployeeDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username);
        
        if (employee == null) {
            throw new UsernameNotFoundException("Employee not found with username: " + username);
        }
        return new CustomEmployeeDetails(employee);
    }
}