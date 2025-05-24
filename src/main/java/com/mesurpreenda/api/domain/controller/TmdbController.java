package com.mesurpreenda.api.domain.controller;

import com.mesurpreenda.api.data.service.TmdbService;
import com.mesurpreenda.api.domain.dto.TmdbDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tmdb")
public class TmdbController {

    @Autowired
    private TmdbService tmdbService;

    @GetMapping("/movie/{id}")
    public Mono<TmdbDTO> getMovieById(@PathVariable Long id) {
        return tmdbService.getMovieById(id);
    }
    @GetMapping("/series/{id}")
    public Mono<TmdbDTO> getSeriesById(@PathVariable Long id) {
        return tmdbService.getSeriesById(id);
    }
}
