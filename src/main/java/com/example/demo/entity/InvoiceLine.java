package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "InvoiceLine")
public class InvoiceLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InvoiceLineId")
    private Integer invoiceLineId;

    @ManyToOne
    @JoinColumn(name = "InvoiceId", nullable = false)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "TrackId", nullable = false)
    private Track track;

    @Column(name = "UnitPrice", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;
}