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
    @GetMapping("/search/series")
    public Mono<TmdbDTO> searchSeries(@RequestParam String query) {
        return tmdbService.searchSeries(query);
    }
    @GetMapping("/discover/movie")
    public Mono<TmdbDTO> discoverMovie() {
        return tmdbService.discoverMovie();
    }
    @GetMapping("/discover/series")
    public Mono<TmdbDTO> discoverSeries() {
        return tmdbService.discoverSeries();
    }
    @GetMapping("/trending/movie/day")
    public Mono<TmdbDTO> getTrendingMoviesDay() {
        return tmdbService.getTrendingMoviesDay();
    }
    @GetMapping("/trending/series/day")
    public Mono<TmdbDTO> getTrendingSeriesDay() {
        return tmdbService.getTrendingSeriesDay();
    }
    @GetMapping("/trending/movie/week")
    public Mono<TmdbDTO> getTrendingMoviesWeek() {
        return tmdbService.getTrendingMoviesWeek();
    }
    @GetMapping("/trending/series/week")
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
    @GetMapping("/upcoming/movie")
    public Mono<TmdbDTO> getUpcomingMovies() {
        return tmdbService.getUpcomingMovies();
    }
    @GetMapping("/upcoming/series")
    public Mono<TmdbDTO> getUpcomingSeries() {
        return tmdbService.getUpcomingSeries();
    }
    @GetMapping("/top-rated/movies")
    public Mono<TmdbDTO> getTopRatedMovies() {
        return tmdbService.getTopRatedMovies();
    }
    @GetMapping("/top-rated/series")
    public Mono<TmdbDTO> getTopRatedSeries() {
        return tmdbService.getTopRatedSeries();
    }
    @GetMapping("movie/details/{id}")
    public Mono<TmdbDTO> getMovieDetails(@PathVariable Long id) {
        return tmdbService.getMovieDetails(id);
    }
    @GetMapping("series/details/{id}")
    public Mono<TmdbDTO> getSeriesDetails(@PathVariable Long id) {
        return tmdbService.getSeriesDetails(id);
    }
    @GetMapping("movie/trailer/{id}")
    public Mono<TmdbDTO> getTrailerByMovieId(@PathVariable Long id) {
        return tmdbService.getTrailerByMovieId(id);
    }
    @GetMapping("series/trailer/{id}")
    public Mono<TmdbDTO> getTrailerBySeriesId(@PathVariable Long id) {
        return tmdbService.getTrailerBySeriesId(id);
    }
    @GetMapping("/movie/provider/{id}")
    public Mono<TmdbDTO> getMovieProviderById(@PathVariable Long id) {
        return tmdbService.getMovieProviders(id);
    }
    @GetMapping("/series/provider/{id}")
    public Mono<TmdbDTO> getSeriesProviderById(@PathVariable Long id) {
        return tmdbService.getSeriesProviders(id);
    }
    @GetMapping("/movie/recommendations/{id}")
    public Mono<TmdbDTO> getMovieRecommendations(@PathVariable Long id) {
        return tmdbService.getMovieRecommendations(id);
    }
    @GetMapping("/series/recommendations/{id}")
    public Mono<TmdbDTO> getSeriesRecommendations(@PathVariable Long id) {
        return tmdbService.getSeriesRecommendations(id);
    }
}
