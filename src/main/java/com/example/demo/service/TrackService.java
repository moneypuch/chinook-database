package com.example.demo.service;

import com.example.demo.entity.Track;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public Track getTrackById(Integer id) {
        return trackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Track not found with id " + id));
    }

    public Track saveTrack(Track track) {
        return trackRepository.save(track);
    }

    public void deleteTrack(Integer id) {
        Track track = getTrackById(id);
        trackRepository.delete(track);
    }

    public Track updateTrack(Integer id, Track trackDetails) {
        Track track = getTrackById(id);

        // Assuming you have the fields `album`, `mediaType`, `genre`, `name`, `composer`, `milliseconds`, etc.
        track.setAlbum(trackDetails.getAlbum());
        track.setMediaType(trackDetails.getMediaType());
        track.setGenre(trackDetails.getGenre());
        track.setName(trackDetails.getName());
        track.setComposer(trackDetails.getComposer());
        track.setMilliseconds(trackDetails.getMilliseconds());
        track.setBytes(trackDetails.getBytes());
        track.setUnitPrice(trackDetails.getUnitPrice());

        return trackRepository.save(track);
    }
}