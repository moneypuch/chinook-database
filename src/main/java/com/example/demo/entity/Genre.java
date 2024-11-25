package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "Genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GenreId")
    private Integer genreId;

    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "genre")
    private List<Track> tracks;
}