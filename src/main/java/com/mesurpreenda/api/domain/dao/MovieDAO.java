package com.mesurpreenda.api.domain.dao;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.domain.repository.MovieRepository;
import com.mesurpreenda.api.util.RandomSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class MovieDAO extends BaseDAO<Movie> {
    @Autowired
    public MovieDAO(MovieRepository movieRepository) {
        this.repository = movieRepository;
    }

    public List<Movie> search(String title) {
        return ((MovieRepository) repository).findByTitleContainingIgnoreCase(title);
    }
    public Optional<Movie> findRandom(){
        return RandomSelector.getRandom(repository.findAll());
    }
}