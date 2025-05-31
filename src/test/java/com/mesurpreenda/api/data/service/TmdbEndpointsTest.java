package com.mesurpreenda.api.data.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TmdbEndpointsTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnApiStatus() {
        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk();
    }


    @Test
    void shouldGetRandomContent() {
        webTestClient.get().uri("/api/random")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetRandomMovie() {
        webTestClient.get().uri("/api/random/movie")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetRandomSeries() {
        webTestClient.get().uri("/api/random/series")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetMovieById() {
        webTestClient.get().uri("/api/tmdb/movie/{id}", 550)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetSeriesById() {
        webTestClient.get().uri("/api/tmdb/series/{id}", 100)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldSearchTmdb() {
        webTestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/api/tmdb/search")
                        .queryParam("query", "Matrix")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldSearchMovies() {
        webTestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/api/tmdb/search/movie")
                        .queryParam("query", "Matrix")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldSearchSeries() {
        webTestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/api/tmdb/search/series")
                        .queryParam("query", "Friends")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldDiscoverMovies() {
        webTestClient.get().uri("/api/tmdb/discover/movie")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldDiscoverSeries() {
        webTestClient.get().uri("/api/tmdb/discover/series")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetTrendingMoviesDay() {
        webTestClient.get().uri("/api/tmdb/trending/movie/day")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetTrendingSeriesDay() {
        webTestClient.get().uri("/api/tmdb/trending/series/day")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetTrendingMoviesWeek() {
        webTestClient.get().uri("/api/tmdb/trending/movie/week")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetTrendingSeriesWeek() {
        webTestClient.get().uri("/api/tmdb/trending/series/week")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetTrendingAllDay() {
        webTestClient.get().uri("/api/tmdb/trending/all/day")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetTrendingAllWeek() {
        webTestClient.get().uri("/api/tmdb/trending/all/week")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetUpcomingMovies() {
        webTestClient.get().uri("/api/tmdb/upcoming/movies")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetTopRatedMovies() {
        webTestClient.get().uri("/api/tmdb/top-rated/movies")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetTopRatedSeries() {
        webTestClient.get().uri("/api/tmdb/top-rated/series")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetMovieDetails() {
        webTestClient.get().uri("/api/tmdb/movie/details/{id}", 550)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetSeriesDetails() {
        webTestClient.get().uri("/api/tmdb/series/details/{id}", 100)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetMovieTrailer() {
        webTestClient.get().uri("/api/tmdb/movie/trailer/{id}", 550)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetSeriesTrailer() {
        webTestClient.get().uri("/api/tmdb/series/trailer/{id}", 100)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetMovieProviders() {
        webTestClient.get().uri("/api/tmdb/movie/provider/{id}", 550)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetSeriesProviders() {
        webTestClient.get().uri("/api/tmdb/series/provider/{id}", 100)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetMovieRecommendations() {
        webTestClient.get().uri("/api/tmdb/movie/recommendations/{id}", 550)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetSeriesRecommendations() {
        webTestClient.get().uri("/api/tmdb/series/recommendations/{id}", 100)
                .exchange()
                .expectStatus().isOk();
    }


}

