package com.example.demo.controller;

import com.example.demo.entity.Invoice;
import com.example.demo.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@Tag(name = "Invoice", description = "The Invoice API")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    @Operation(summary = "Get all invoices")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an invoice by ID")
    public ResponseEntity<Invoice> getInvoiceById(
            @Parameter(description = "ID of the invoice to be retrieved") @PathVariable Integer id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }

    @PostMapping
    @Operation(summary = "Create a new invoice")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        Invoice savedInvoice = invoiceService.saveInvoice(invoice);
        return ResponseEntity.ok(savedInvoice);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing invoice")
    public ResponseEntity<Invoice> updateInvoice(
            @Parameter(description = "ID of the invoice to be updated") @PathVariable Integer id, 
            @RequestBody Invoice invoiceDetails) {
        Invoice updatedInvoice = invoiceService.updateInvoice(id, invoiceDetails);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an invoice by ID")
    public ResponseEntity<Void> deleteInvoice(
            @Parameter(description = "ID of the invoice to be deleted") @PathVariable Integer id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}