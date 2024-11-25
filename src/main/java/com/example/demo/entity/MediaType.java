package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "MediaType")
public class MediaType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MediaTypeId")
    private Integer mediaTypeId;

    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "mediaType")
    private List<Track> tracks;
}