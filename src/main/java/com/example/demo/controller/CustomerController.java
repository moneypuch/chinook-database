package com.example.demo.controller;

import com.example.demo.dto.CustomerDto;
import com.example.demo.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer", description = "The Customer API")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @Operation(summary = "Get all customers with pagination")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(
            @RequestParam(value = "range") String rangeParam,
            @RequestParam(value = "sort") String sortParam) throws JsonProcessingException {

        // Parse range and sort parameters
        ObjectMapper objectMapper = new ObjectMapper();
        Integer[] range = objectMapper.readValue(rangeParam, Integer[].class);
        String[] sort = objectMapper.readValue(sortParam, String[].class);

        // Calculate page number and perPage size from range[0] and range[1]
        // Here, range[0] is the starting index and range[1] is the ending index.
        // For example, range=[0,19] means fetching the first 20 records.
        int page = range[0] / (range[1] - range[0] + 1);
        int perPage = range[1] - range[0] + 1;

        String sortBy = sort[0];
        String order = sort[1];

        log.info("Received parameters - page: {}, perPage: {}, sort: {}, order: {}", page, perPage, sortBy, order);

        List<CustomerDto> customers = customerService.getPaginatedCustomers(page, perPage, sortBy, order);
        long totalCustomers = customerService.countCustomers();

        // Add Content-Range header for client-side pagination
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Range", "customers " + range[0] + "-" + range[1] + "/" + totalCustomers);

        return ResponseEntity.ok().headers(headers).body(customers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a customer by ID")
    public ResponseEntity<CustomerDto> getCustomerById(
            @Parameter(description = "ID of the customer to be retrieved") @PathVariable Integer id) {
        CustomerDto customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    @Operation(summary = "Create a new customer")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto savedCustomer = customerService.saveCustomer(customerDto);
        return ResponseEntity.ok(savedCustomer);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing customer")
    public ResponseEntity<CustomerDto> updateCustomer(
            @Parameter(description = "ID of the customer to be updated") @PathVariable Integer id,
            @RequestBody CustomerDto customerDetails) {
        customerDetails.setId(id);
        CustomerDto updatedCustomer = customerService.saveCustomer(customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer by ID")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "ID of the customer to be deleted") @PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}