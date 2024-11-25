package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "Playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlaylistId")
    private Integer playlistId;

    @Column(name = "Name")
    private String name;

    @ManyToMany
    @JoinTable(
        name = "PlaylistTrack",
        joinColumns = @JoinColumn(name = "PlaylistId"),
        inverseJoinColumns = @JoinColumn(name = "TrackId")
    )
    private List<Track> tracks;
}