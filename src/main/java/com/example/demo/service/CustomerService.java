package com.example.demo.service;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.Employee;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.repository.CustomerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final SecurityService securityService;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, SecurityService securityService) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.securityService = securityService;
    }

    // Method to get paginated customers
    public List<CustomerDto> getPaginatedCustomers(int page, int perPage, String sort, String order) {
        Employee loggedEmployee = securityService.getLoggedEmployee();
        boolean isManager = securityService.isUserManager();

        if ("id".equalsIgnoreCase(sort)) {
            sort = "customerId";
        }

        Sort.Direction direction = Sort.Direction.fromString(order);
        PageRequest pageRequest = PageRequest.of(page, perPage, direction, sort);

        if (isManager) {
            return customerRepository.findAll(pageRequest)
                    .stream()
                    .map(customerMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return customerRepository.findBySupportRep(loggedEmployee, pageRequest)
                    .stream()
                    .map(customerMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    // Method to count the total number of customers
    public long countCustomers() {
        return customerRepository.count();
    }

    public CustomerDto getCustomerById(Integer id) {
        return customerMapper.toDto(
                customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found")));
    }

    public CustomerDto saveCustomer(CustomerDto customerDto) {
        return customerMapper.toDto(
                customerRepository.save(
                        customerMapper.toEntity(customerDto)));
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}