package com.example.demo.service;

import com.example.demo.entity.InvoiceLine;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.InvoiceLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceLineService {

    @Autowired
    private InvoiceLineRepository invoiceLineRepository;

    public List<InvoiceLine> getAllInvoiceLines() {
        return invoiceLineRepository.findAll();
    }

    public InvoiceLine getInvoiceLineById(Integer id) {
        return invoiceLineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InvoiceLine not found with id " + id));
    }

    public InvoiceLine saveInvoiceLine(InvoiceLine invoiceLine) {
        return invoiceLineRepository.save(invoiceLine);
    }

    public void deleteInvoiceLine(Integer id) {
        InvoiceLine invoiceLine = getInvoiceLineById(id);
        invoiceLineRepository.delete(invoiceLine);
    }

    public InvoiceLine updateInvoiceLine(Integer id, InvoiceLine invoiceLineDetails) {
        InvoiceLine invoiceLine = getInvoiceLineById(id);

        // Assuming you have fields: `invoice`, `track`, `unitPrice`, `quantity`.
        invoiceLine.setInvoice(invoiceLineDetails.getInvoice());
        invoiceLine.setTrack(invoiceLineDetails.getTrack());
        invoiceLine.setUnitPrice(invoiceLineDetails.getUnitPrice());
        invoiceLine.setQuantity(invoiceLineDetails.getQuantity());

        return invoiceLineRepository.save(invoiceLine);
    }
}