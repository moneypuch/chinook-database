package com.example.demo.mapper;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public CustomerDto toDto(Customer customer) {
        return new CustomerDto(
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getCity(),
                customer.getCompany(),
                customer.getCountry(),
                customer.getPhone(),
                customer.getPostalCode(),
                customer.getState()
        );
    }

    public Customer toEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDto.getId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setAddress(customerDto.getAddress());
        customer.setCity(customerDto.getCity());
        customer.setCompany(customerDto.getCompany());
        customer.setCountry(customerDto.getCountry());
        customer.setPhone(customerDto.getPhone());
        customer.setPostalCode(customerDto.getPostalCode());
        customer.setState(customerDto.getState());
        return customer;
    }

    public List<CustomerDto> toDtoList(List<Customer> customers) {
        return customers.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Customer> toEntityList(List<CustomerDto> customerDtos) {
        return customerDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}