package com.mesurpreenda.api.data.service;

import com.mesurpreenda.api.domain.dto.ProvidersDTO;
import com.mesurpreenda.api.domain.dto.TmdbResponseDTO;
import com.mesurpreenda.api.domain.dto.TmdbResultDTO;
import com.mesurpreenda.api.domain.dto.TrailersDTO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class TmdbService {
    private final WebClient webClient;

    @Setter
    @Value("${tmdb.api.key}")
    private String apiKey;

    public TmdbService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.themoviedb.org/3").build();
    }

    private <T> Mono<T> get(String path,
                            ParameterizedTypeReference<T> typeRef,
                            Object... uriVariables) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("api_key", apiKey)
                        .queryParam("language", "pt-BR")
                        .build(uriVariables))
                .retrieve()
                .bodyToMono(typeRef);
    }

    private <T> Mono<T> getWithQuery(String path,
                                     String query,
                                     ParameterizedTypeReference<T> typeRef,
                                     Object... uriVariables) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("api_key", apiKey)
                        .queryParam("query", query)
                        .queryParam("language", "pt-BR")
                        .build(uriVariables))
                .retrieve()
                .bodyToMono(typeRef);
    }

    private Mono<TmdbResultDTO> getWithId(String path, Long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("api_key", apiKey)
                        .queryParam("language", "pt-BR")
                        .build(id))
                .retrieve()
                .bodyToMono(TmdbResultDTO.class);
    }

    private <T> Mono<T> getWithBearerToken(String path,
                                           ParameterizedTypeReference<T> typeRef,
                                           Object... uriVariables) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("api_key", apiKey)
                        .queryParam("language", "pt-BR")
                        .build(uriVariables))
                .retrieve()
                .bodyToMono(typeRef);
    }

    private Mono<TrailersDTO> getTrailers(String path, Long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("api_key", apiKey)
                        .queryParam("language", "pt-BR")
                        .build(id))
                .retrieve()
                .bodyToMono(TrailersDTO.class);
    }

    private Mono<ProvidersDTO> getProviders(String path, Long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("api_key", apiKey)
                        .build(id))
                .retrieve()
                .bodyToMono(ProvidersDTO.class);
    }

    public Mono<TmdbResultDTO> getMovieById(Long movieId) {
        return getWithId("/movie/{movie_id}", movieId);
    }

    public Mono<TmdbResultDTO> getSeriesById(Long seriesId) {
        return getWithId("/tv/{tv_id}", seriesId);
    }

    public Mono<TmdbResponseDTO<TmdbResultDTO>> searchBoth(String query) {
        return getWithQuery(
                "/search/multi",
                query,
                new ParameterizedTypeReference<TmdbResponseDTO<TmdbResultDTO>>() {
                }
        );
    }

    public Mono<TmdbResponseDTO<TmdbResultDTO>> searchMovie(String query) {
        return getWithQuery(
                "/search/movie",
                query,
                new ParameterizedTypeReference<TmdbResponseDTO<TmdbResultDTO>>() {
                }
        );
    }

    public Mono<TmdbResponseDTO<TmdbResultDTO>> searchSeries(String query) {
        return getWithQuery(
                "/search/tv",
                query,
                new ParameterizedTypeReference<TmdbResponseDTO<TmdbResultDTO>>() {
                }
        );
    }

    public Mono<TmdbResponseDTO<TmdbResultDTO>> discoverMovie() {
        return get(
                "/discover/movie",
                new ParameterizedTypeReference<TmdbResponseDTO<TmdbResultDTO>>() {
                }
        );
    }

    public Mono<TmdbResponseDTO<TmdbResultDTO>> discoverSeries() {
        return get(
                "/discover/tv",
                new ParameterizedTypeReference<TmdbResponseDTO<TmdbResultDTO>>() {
                }
        );
    }

    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTrendingDay() {
        return get(
                "/trending/all/day",
                new ParameterizedTypeReference<TmdbResponseDTO<TmdbResultDTO>>() {
                }
        );
    }

    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTrendingWeek() {
        return get(
                "/trending/all/week",
                new ParameterizedTypeReference<TmdbResponseDTO<TmdbResultDTO>>() {
                }
        );
    }

    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTrendingMoviesDay() {
        return get(
                "/trending/movie/day",
                new ParameterizedTypeReference<TmdbResponseDTO<TmdbResultDTO>>() {
                }
        );
    }

    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTrendingMoviesWeek() {
        return get(
                "/trending/movie/week",
                new ParameterizedTypeReference<TmdbResponseDTO<TmdbResultDTO>>() {
                }
        );
    }

    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTrendingSeriesDay() {
        return get(
                "/trending/tv/day",
                new ParameterizedTypeReference<TmdbResponseDTO<TmdbResultDTO>>() {
                }
        );
    }

    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTrendingSeriesWeek() {
        return get(
                "/trending/tv/week",
                new ParameterizedTypeReference<TmdbResponseDTO<TmdbResultDTO>>() {
                }
        );
    }

    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTopRatedMovies() {
        return get(
                "/movie/top_rated",
                new ParameterizedTypeReference<TmdbResponseDTO<TmdbResultDTO>>() {
                }
        );
    }

    public Mono<TmdbResponseDTO<TmdbResultDTO>> getTopRatedSeries() {
        return get(
                "/tv/top_rated",
                new ParameterizedTypeReference<TmdbResponseDTO<TmdbResultDTO>>() {
                }
        );
    }

    public Mono<TmdbResponseDTO<TmdbResultDTO>> getUpcomingMovies() {
        return getWithBearerToken(
                "/movie/upcoming",
                new ParameterizedTypeReference<TmdbResponseDTO<TmdbResultDTO>>() {
                });
    }

    public Mono<TrailersDTO> getTrailerByMovieId(Long movieId) {
        return getTrailers("/movie/{movie_id}/videos", movieId);
    }

    public Mono<TrailersDTO> getTrailerBySeriesId(Long seriesId) {
        return getTrailers("/tv/{tv_id}/videos", seriesId);
    }

    public Mono<TmdbResultDTO> getMovieDetails(Long movieId) {
        return getWithId("/movie/{movie_id}", movieId);
    }

    public Mono<TmdbResultDTO> getSeriesDetails(Long seriesId) {
        return getWithId("/tv/{tv_id}", seriesId);
    }

    public Mono<ProvidersDTO> getMovieProviders(Long movieId) {
        return getProviders("/movie/{movie_id}/watch/providers", movieId);
    }

    public Mono<ProvidersDTO> getSeriesProviders(Long seriesId) {
        return getProviders("/tv/{tv_id}/watch/providers", seriesId);
    }

    public Mono<TmdbResultDTO> getMovieRecommendations(Long movieId) {
        return getWithId("/movie/{movie_id}/recommendations", movieId);
    }

    public Mono<TmdbResultDTO> getSeriesRecommendations(Long seriesId) {
        return getWithId("/tv/{tv_id}/recommendations", seriesId);
    }
}
