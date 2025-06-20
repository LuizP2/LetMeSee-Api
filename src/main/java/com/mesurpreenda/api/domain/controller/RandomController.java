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
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class RandomController {
    @Autowired
    private MovieDAO movieDAO;
    @Autowired
    private SeriesDAO seriesDAO;

    @GetMapping("/random")
    public ResponseEntity<Object> random() {
        List<Object> allContent = getAllContent();
        
        if (allContent.isEmpty()) {
            return createNotFoundResponse();
        }
        
        return ResponseEntity.ok(getRandomContent(allContent));
    }

    private List<Object> getAllContent() {
        List<Object> allContent = new ArrayList<>();
        allContent.addAll(movieDAO.findAll());
        allContent.addAll(seriesDAO.findAll());
        return allContent;
    }

    private Object getRandomContent(List<Object> content) {
        return content.get(new Random().nextInt(content.size()));
    }

    private ResponseEntity<Object> createNotFoundResponse() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("No content available");
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
