package com.mesurpreenda.api.data.service;

import com.mesurpreenda.api.domain.dto.TmdbDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class TmdbService {
    private final WebClient webClient;

    @Value("${tmdb.api.key}")
    private String apiKey;

    public TmdbService(@Value("${tmdb.api.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    private Mono<TmdbDTO> get(String path, Object... uriVariables) {
        return webClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder
                            .path(path)
                            .queryParam("api_key", apiKey);
                    return builder.build(uriVariables);
                })
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }

    private Mono<TmdbDTO> getWithQuery(String path, String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("api_key", apiKey)
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    private Mono<TmdbDTO> getWithId(String path, Long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("api_key", apiKey)
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }

    public Mono<TmdbDTO> getMovieById(Long movieId) {
        return get("/movie/{movie_id}", movieId);
    }

    public Mono<TmdbDTO> getSeriesById(Long seriesId) {
        return get("/tv/{tv_id}", seriesId);
    }

    public Mono<TmdbDTO> searchBoth(String query) {
        return getWithQuery("/search/multi", query);
    }

    public Mono<TmdbDTO> searchMovie(String query) {
        return getWithQuery("/search/movie", query);
    }

    public Mono<TmdbDTO> searchSeries(String query) {
        return getWithQuery("/search/tv", query);
    }

    public Mono<TmdbDTO> discoverMovie() {
        return get("/discover/movie");
    }

    public Mono<TmdbDTO> discoverSeries() {
        return get("/discover/tv");
    }

    public Mono<TmdbDTO> getTrendingDay() {
        return get("/trending/all/day");
    }

    public Mono<TmdbDTO> getTrendingWeek() {
        return get("/trending/all/week");
    }

    public Mono<TmdbDTO> getTrendingMoviesDay() {
        return get("/trending/movie/day");
    }

    public Mono<TmdbDTO> getTrendingMoviesWeek() {
        return get("/trending/movie/week");
    }

    public Mono<TmdbDTO> getTrendingSeriesDay() {
        return get("/trending/tv/day");
    }

    public Mono<TmdbDTO> getTrendingSeriesWeek() {
        return get("/trending/tv/week");
    }

    public Mono<TmdbDTO> getTopRatedMovies(){
        return get("/movie/top_rated");
    }

    public Mono<TmdbDTO> getTopRatedSeries(){
        return get("/tv/top_rated");
    }

    public Mono<TmdbDTO> getUpcomingMovies(){
        return get("/movie/upcoming");
    }
    public Mono<TmdbDTO> getUpcomingSeries() {
        return get("/tv/upcoming");
    }
    public Mono<TmdbDTO> getTrailerByMovieId(Long movieId) {
        return getWithId("/movie/{movie_id}/videos", movieId);
    }
    public Mono<TmdbDTO> getTrailerBySeriesId(Long seriesId) {
        return getWithId("/tv/{tv_id}/videos", seriesId);
    }
    public Mono<TmdbDTO> getMovieDetails(Long movieId) {
        return getWithId("/movie/{movie_id}", movieId);
    }
    public Mono<TmdbDTO> getSeriesDetails(Long seriesId) {
        return getWithId("/tv/{tv_id}", seriesId);
    }
    public Mono<TmdbDTO> getMovieProviders(Long movieId) {
        return getWithId("/movie/{movie_id}/watch/providers", movieId);
    }
    public Mono<TmdbDTO> getSeriesProviders(Long seriesId) {
        return getWithId("/tv/{tv_id}/watch/providers", seriesId);
    }
    public Mono<TmdbDTO> getMovieRecommendations(Long movieId) {
        return getWithId("/movie/{movie_id}/recommendations", movieId);
    }
    public Mono<TmdbDTO> getSeriesRecommendations(Long seriesId) {
        return getWithId("/tv/{tv_id}/recommendations", seriesId);
    }
}
