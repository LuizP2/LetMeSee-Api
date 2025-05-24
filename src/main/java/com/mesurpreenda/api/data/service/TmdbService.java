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
}
