package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.PasswordHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class PasswordService {

    private final static String DEFAULT_PASSWORD = "Jo5hu4!";
    private final static int PASSWORD_HISTORY_LIMIT = 5;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordHistoryRepository passwordHistoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{6,14}$";

    public boolean changePassword(String username, String newPassword) {
        Employee employee = employeeRepository.findByUsername(username);
        if (employee == null || !isValidPassword(newPassword) || isRecentPassword(employee, newPassword)) {
            return false;
        }

        // Save password to history
        passwordHistoryRepository.savePasswordHistory(employee.getEmployeeId(), employee.getPassword());

        // Update the employee's password
        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeRepository.save(employee);

        return true;
    }

    private boolean isValidPassword(String password) {
        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
    }

    private boolean isRecentPassword(Employee employee, String newPassword) {
        // Use Pageable to limit the number of results
        Pageable pageable = PageRequest.of(0, PASSWORD_HISTORY_LIMIT, Sort.by("changedOn").descending());

        // Fetch recent passwords
        List<String> recentPasswords = passwordHistoryRepository.findRecentPasswords(employee.getEmployeeId(), pageable).getContent();

        // Check if the new password matches any of the recent passwords
        return recentPasswords.stream().anyMatch(ph -> passwordEncoder.matches(newPassword, ph));
    }
}