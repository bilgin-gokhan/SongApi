package com.gokhanbilgin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gokhanbilgin.model.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {

}
