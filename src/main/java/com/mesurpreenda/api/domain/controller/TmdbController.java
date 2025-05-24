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
    @GetMapping("/search")
    public Mono<TmdbDTO> searchBoth(@RequestParam String query) {
        return tmdbService.searchBoth(query);
    }
    @GetMapping("/search/movie")
    public Mono<TmdbDTO> searchMovie(@RequestParam String query) {
        return tmdbService.searchMovie(query);
    }
    @GetMapping("/search/tv")
    public Mono<TmdbDTO> searchSeries(@RequestParam String query) {
        return tmdbService.searchSeries(query);
    }
    @GetMapping("/discover/movie")
    public Mono<TmdbDTO> discoverMovie() {
        return tmdbService.discoverMovie();
    }
    @GetMapping("/discover/tv")
    public Mono<TmdbDTO> discoverSeries() {
        return tmdbService.discoverSeries();
    }
    @GetMapping("/trending/movie/day")
    public Mono<TmdbDTO> getTrendingMoviesDay() {
        return tmdbService.getTrendingMoviesDay();
    }
    @GetMapping("/trending/tv/day")
    public Mono<TmdbDTO> getTrendingSeriesDay() {
        return tmdbService.getTrendingSeriesDay();
    }
    @GetMapping("/trending/movie/week")
    public Mono<TmdbDTO> getTrendingMoviesWeek() {
        return tmdbService.getTrendingMoviesWeek();
    }
    @GetMapping("/trending/tv/week")
    public Mono<TmdbDTO> getTrendingSeriesWeek() {
        return tmdbService.getTrendingSeriesWeek();
    }
    @GetMapping("/trending/all/day")
    public Mono<TmdbDTO> getTrendingDay() {
        return tmdbService.getTrendingDay();
    }
    @GetMapping("/trending/all/week")
    public Mono<TmdbDTO> getTrendingWeek() {
        return tmdbService.getTrendingWeek();
    }
}
