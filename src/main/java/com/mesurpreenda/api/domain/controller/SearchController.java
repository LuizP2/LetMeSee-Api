package com.mesurpreenda.api.domain.controller;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.entity.Series;
import com.mesurpreenda.api.domain.dao.MovieDAO;
import com.mesurpreenda.api.domain.dao.SeriesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SearchController {
    @Autowired
    private MovieDAO movieDAO;
    @Autowired
    private SeriesDAO seriesDAO;

    @GetMapping("/search")
    public List<Object> search(@RequestParam String query) {
        List<Object> results = new ArrayList<>();
        results.addAll(movieDAO.search(query));
        results.addAll(seriesDAO.search(query));
        return results;
    }

    @GetMapping("/search/movies")
    public List<Movie> searchMovies(@RequestParam String query) {
        return movieDAO.search(query);
    }

    @GetMapping("/search/series")
    public List<Series> searchSeries(@RequestParam String query) {
        return seriesDAO.search(query);
    }

    @GetMapping("/random")
    public ResponseEntity<Object> random() {
        Optional<Movie> randomMovie = movieDAO.findRandom();
        Optional<Series> randomSeries = seriesDAO.findRandom();
        return randomMovie.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> randomSeries.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("No content available")));
    }

    @GetMapping("/random/movie")
    public Movie randomMovie() {
        return movieDAO.findRandom().orElse(null);
    }

    @GetMapping("/random/series")
    public Series randomSeries() {
        return seriesDAO.findRandom().orElse(null);
    }
}
