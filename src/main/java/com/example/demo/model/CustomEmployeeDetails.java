package com.example.demo.model;

import com.example.demo.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class CustomEmployeeDetails implements UserDetails {

    private Employee employee;

    public CustomEmployeeDetails(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Here you should map your employee roles to a collection of GrantedAuthority
        if (employee.getTitle() != null && employee.getTitle().toLowerCase().contains("manager")) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }
        return Collections.emptyList();  // Change this to your actual roles logic if any
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}