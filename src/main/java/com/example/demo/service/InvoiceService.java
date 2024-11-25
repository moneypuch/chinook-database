package com.example.demo.service;

import com.example.demo.entity.Invoice;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Integer id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id " + id));
    }

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Integer id) {
        Invoice invoice = getInvoiceById(id);
        invoiceRepository.delete(invoice);
    }

    public Invoice updateInvoice(Integer id, Invoice invoiceDetails) {
        Invoice invoice = getInvoiceById(id);

        // Assuming you have fields `customer`, `invoiceDate`, `billingAddress`, etc.
        invoice.setCustomer(invoiceDetails.getCustomer());
        invoice.setInvoiceDate(invoiceDetails.getInvoiceDate());
        invoice.setBillingAddress(invoiceDetails.getBillingAddress());
        invoice.setBillingCity(invoiceDetails.getBillingCity());
        invoice.setBillingState(invoiceDetails.getBillingState());
        invoice.setBillingCountry(invoiceDetails.getBillingCountry());
        invoice.setBillingPostalCode(invoiceDetails.getBillingPostalCode());
        invoice.setTotal(invoiceDetails.getTotal());

        return invoiceRepository.save(invoice);
    }
}