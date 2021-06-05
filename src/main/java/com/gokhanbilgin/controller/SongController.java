package com.gokhanbilgin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gokhanbilgin.model.Song;
import com.gokhanbilgin.repository.SongRepository;

@RestController
public class SongController {
	@Autowired
	private SongRepository songRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/song")
	public ResponseEntity<String> createSong(@RequestBody Song song) {
		try {
			songRepository.save(song);
			return new ResponseEntity(song.getTitle() + " succesfully added.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/song")
	public ResponseEntity getAllSong() {
		List<Song> songs = songRepository.findAll();
		if (songs.size() > 0) {
			return new ResponseEntity(songs, HttpStatus.OK);
		} else {
			return new ResponseEntity("No songs found!", HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/song/{id}")
	public ResponseEntity deleteSongById(@PathVariable("id") int id) {
		try {
			songRepository.deleteById(id);
			return new ResponseEntity(id + " succesfully deleted!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/song/{id}")
	public ResponseEntity updateById(@PathVariable("id") int id, @RequestBody Song newSong) {
		Optional<Song> songOptional = songRepository.findById(id);
		if (songOptional.isPresent()) {
			Song songToSave = songOptional.get();
			songToSave.setTitle(newSong.getTitle());
			songToSave.setArtistName(newSong.getArtistName());
			songToSave.setGenre(newSong.getGenre());
			songRepository.save(songToSave);
			return new ResponseEntity(id + " succesfully updated.", HttpStatus.OK);
		} else {
			return new ResponseEntity("No Song with id: " + id + " found!", HttpStatus.NOT_FOUND);
		}
	}
}
