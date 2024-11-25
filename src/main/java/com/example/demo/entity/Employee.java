package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeId")
    private Integer employeeId;

    @Column(name = "LastName", nullable = false)
    private String lastName;

    @Column(name = "FirstName", nullable = false)
    private String firstName;

    @Column(name = "Title")
    private String title;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "ReportsTo")
    private Employee reportsTo;

    @Column(name = "BirthDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @Column(name = "HireDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hireDate;

    @Column(name = "Address")
    private String address;

    @Column(name = "City")
    private String city;

    @Column(name = "State")
    private String state;

    @Column(name = "Country")
    private String country;

    @Column(name = "PostalCode")
    private String postalCode;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Fax")
    private String fax;

    @Column(name = "Email")
    private String email;

    @OneToMany(mappedBy = "supportRep")
    private List<Customer> customers;

    @OneToMany(mappedBy = "reportsTo")
    private List<Employee> subordinates;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PasswordHistory> passwordHistories;
}