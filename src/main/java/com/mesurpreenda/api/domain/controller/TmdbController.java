package com.mesurpreenda.api.domain.controller;

import com.mesurpreenda.api.data.service.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tmdb")
public class TmdbController {

    @Autowired
    private TmdbService tmdbService;

    @GetMapping("/movie/{id}")
    public Mono<Object> getMovieById(@PathVariable Long id) {
        return tmdbService.getMovieById(id);
    }
}
