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

    public Mono<TmdbDTO> getMovieById(Long movieId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{movie_id}")
                        .queryParam("api_key", apiKey)
                        .build(movieId))
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }

    public Mono<TmdbDTO> getSeriesById(Long seriesId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tv/{tv_id}")
                        .queryParam("api_key", apiKey)
                        .build(seriesId))
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> searchBoth(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/multi")
                        .queryParam("api_key", apiKey)
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> searchMovie(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("api_key", apiKey)
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> searchSeries(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/tv")
                        .queryParam("api_key", apiKey)
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> discoverMovie() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/discover/movie")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> discoverSeries() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/discover/tv")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> getTrendingDay() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trending/all/day")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> getTrendingWeek() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trending/all/week")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> getTrendingMoviesDay() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trending/movie/day")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> getTrendingMoviesWeek() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trending/movie/week")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> getTrendingSeriesDay() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trending/tv/day")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> getTrendingSeriesWeek() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trending/tv/week")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> getTopRatedMovies(){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/top_rated")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> getTopRatedSeries(){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tv/top_rated")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> getUpcomingMovies(){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/upcoming")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
    public Mono<TmdbDTO> getUpcomingSeries(){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tv/upcoming")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(TmdbDTO.class);
    }
}
