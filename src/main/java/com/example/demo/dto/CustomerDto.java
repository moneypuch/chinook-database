package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String city;
    private String company;
    private String country;
    private String phone;
    private String postalCode;
    private String state;
}