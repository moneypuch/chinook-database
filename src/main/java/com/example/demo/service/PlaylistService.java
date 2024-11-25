package com.example.demo.service;

import com.example.demo.entity.Playlist;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist getPlaylistById(Integer id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id " + id));
    }

    public Playlist savePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(Integer id) {
        Playlist playlist = getPlaylistById(id);
        playlistRepository.delete(playlist);
    }

    public Playlist updatePlaylist(Integer id, Playlist playlistDetails) {
        Playlist playlist = getPlaylistById(id);

        // Assuming you have field `name` and `tracks`.
        playlist.setName(playlistDetails.getName());
        playlist.setTracks(playlistDetails.getTracks());

        return playlistRepository.save(playlist);
    }
}