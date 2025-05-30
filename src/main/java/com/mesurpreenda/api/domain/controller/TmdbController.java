package com.mesurpreenda.api.domain.controller;

import com.mesurpreenda.api.data.service.TmdbService;
import com.mesurpreenda.api.domain.dto.TmdbResponseDTO;
import com.mesurpreenda.api.domain.dto.TmdbResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tmdb")
public class TmdbController {

    @Autowired
    private TmdbService tmdbService;

    @GetMapping("/movie/{id}")
    public Mono<TmdbResultDTO> getMovieById(@PathVariable Long id) {
        return tmdbService.getMovieById(id);
    }

    @GetMapping("/series/{id}")
    public Mono<TmdbResultDTO> getSeriesById(@PathVariable Long id) {
        return tmdbService.getSeriesById(id);
    }

    @GetMapping("/search")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> searchBoth(@RequestParam String query) {
        return tmdbService.searchBoth(query);
    }

    @GetMapping("/search/movie")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> searchMovie(@RequestParam String query) {
        return tmdbService.searchMovie(query);
    }

    @GetMapping("/search/series")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> searchSeries(@RequestParam String query) {
        return tmdbService.searchSeries(query);
    }

    @GetMapping("/discover/movie")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> discoverMovie() {
        return tmdbService.discoverMovie();
    }

    @GetMapping("/discover/series")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> discoverSeries() {
        return tmdbService.discoverSeries();
    }

    @GetMapping("/trending/movie/day")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTrendingMoviesDay() {
        return tmdbService.getTrendingMoviesDay();
    }

    @GetMapping("/trending/series/day")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTrendingSeriesDay() {
        return tmdbService.getTrendingSeriesDay();
    }

    @GetMapping("/trending/movie/week")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTrendingMoviesWeek() {
        return tmdbService.getTrendingMoviesWeek();
    }

    @GetMapping("/trending/series/week")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTrendingSeriesWeek() {
        return tmdbService.getTrendingSeriesWeek();
    }

    @GetMapping("/trending/all/day")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTrendingDay() {
        return tmdbService.getTrendingDay();
    }

    @GetMapping("/trending/all/week")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTrendingWeek() {
        return tmdbService.getTrendingWeek();
    }

    @GetMapping("/upcoming/movies")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> getUpcomingMovies() {
        return tmdbService.getUpcomingMovies();
    }

    @GetMapping("/upcoming/series")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> getUpcomingSeries() {
        return tmdbService.getUpcomingSeries();
    }

    @GetMapping("/top-rated/movies")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTopRatedMovies() {
        return tmdbService.getTopRatedMovies();
    }

    @GetMapping("/top-rated/series")
    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTopRatedSeries() {
        return tmdbService.getTopRatedSeries();
    }

    @GetMapping("movie/details/{id}")
    public Mono<TmdbResultDTO> getMovieDetails(@PathVariable Long id) {
        return tmdbService.getMovieDetails(id);
    }

    @GetMapping("series/details/{id}")
    public Mono<TmdbResultDTO> getSeriesDetails(@PathVariable Long id) {
        return tmdbService.getSeriesDetails(id);
    }

    @GetMapping("movie/trailer/{id}")
    public Mono<TmdbResultDTO> getTrailerByMovieId(@PathVariable Long id) {
        return tmdbService.getTrailerByMovieId(id);
    }

    @GetMapping("series/trailer/{id}")
    public Mono<TmdbResultDTO> getTrailerBySeriesId(@PathVariable Long id) {
        return tmdbService.getTrailerBySeriesId(id);
    }

    @GetMapping("/movie/provider/{id}")
    public Mono<TmdbResultDTO> getMovieProviderById(@PathVariable Long id) {
        return tmdbService.getMovieProviders(id);
    }

    @GetMapping("/series/provider/{id}")
    public Mono<TmdbResultDTO> getSeriesProviderById(@PathVariable Long id) {
        return tmdbService.getSeriesProviders(id);
    }

    @GetMapping("/movie/recommendations/{id}")
    public Mono<TmdbResultDTO> getMovieRecommendations(@PathVariable Long id) {
        return tmdbService.getMovieRecommendations(id);
    }

    @GetMapping("/series/recommendations/{id}")
    public Mono<TmdbResultDTO> getSeriesRecommendations(@PathVariable Long id) {
        return tmdbService.getSeriesRecommendations(id);
    }
}
