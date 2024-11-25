package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "Track")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TrackId")
    private Integer trackId;

    @Column(name = "Name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "AlbumId")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "MediaTypeId", nullable = false)
    private MediaType mediaType;

    @ManyToOne
    @JoinColumn(name = "GenreId")
    private Genre genre;

    @Column(name = "Composer")
    private String composer;

    @Column(name = "Milliseconds", nullable = false)
    private Integer milliseconds;

    @Column(name = "Bytes")
    private Integer bytes;

    @Column(name = "UnitPrice", nullable = false)
    private BigDecimal unitPrice;

    @OneToMany(mappedBy = "track")
    private List<InvoiceLine> invoiceLines;

    @ManyToMany(mappedBy = "tracks")
    private List<Playlist> playlists;
}