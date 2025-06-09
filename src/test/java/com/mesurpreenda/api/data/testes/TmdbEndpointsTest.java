package com.mesurpreenda.api.data.testes;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TmdbEndpointsTest {

    @Autowired
    private WebTestClient webTestClient;

    @Nested
    @DisplayName("Random Endpoints")
    class RandomEndpoints {
        @Test @Order(1)
        void shouldReturnApiStatus() {
            webTestClient.get().uri("/")
                    .exchange()
                    .expectStatus().isOk();
        }

        @Test @Order(2)
        void shouldGetRandomContent() {
            webTestClient.get().uri("/api/random")
                    .exchange()
                    .expectStatus().isOk();
        }

        @Test @Order(3)
        void shouldGetRandomMovie() {
            webTestClient.get().uri("/api/random/movie")
                    .exchange()
                    .expectStatus().isOk();
        }

        @Test @Order(4)
        void shouldGetRandomSeries() {
            webTestClient.get().uri("/api/random/series")
                    .exchange()
                    .expectStatus().isOk();
        }
    }

    @Nested
    @DisplayName("TMDB Detail Endpoints")
    class DetailEndpoints {
        @Test @Order(5)
        void shouldGetMovieById() {
            webTestClient.get().uri("/api/tmdb/movie/{id}", 550)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(550);
        }

        @Test @Order(6)
        void shouldGetSeriesById() {
            webTestClient.get().uri("/api/tmdb/series/{id}", 100)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(100);
        }
    }

    @Nested
    @DisplayName("TMDB Search Endpoints")
    class SearchEndpoints {
        @Test @Order(7)
        void shouldSearchTmdb() {
            webTestClient.get().uri(uriBuilder -> uriBuilder
                            .path("/api/tmdb/search")
                            .queryParam("query", "Matrix")
                            .build())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test @Order(8)
        void shouldSearchMovies() {
            webTestClient.get().uri(uriBuilder -> uriBuilder
                            .path("/api/tmdb/search/movie")
                            .queryParam("query", "Matrix")
                            .build())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test @Order(9)
        void shouldSearchSeries() {
            webTestClient.get().uri(uriBuilder -> uriBuilder
                            .path("/api/tmdb/search/series")
                            .queryParam("query", "Friends")
                            .build())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }
    }

    @Nested
    @DisplayName("TMDB Discovery Endpoints")
    class DiscoveryEndpoints {
        @Test @Order(10)
        void shouldDiscoverMovies() {
            webTestClient.get().uri("/api/tmdb/discover/movie")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test @Order(11)
        void shouldDiscoverSeries() {
            webTestClient.get().uri("/api/tmdb/discover/series")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }
    }

    @Nested
    @DisplayName("TMDB Trending Endpoints")
    class TrendingEndpoints {
        @Test @Order(12)
        void shouldGetTrendingMoviesDay() {
            webTestClient.get().uri("/api/tmdb/trending/movie/day")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test @Order(13)
        void shouldGetTrendingSeriesDay() {
            webTestClient.get().uri("/api/tmdb/trending/series/day")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test @Order(14)
        void shouldGetTrendingMoviesWeek() {
            webTestClient.get().uri("/api/tmdb/trending/movie/week")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test @Order(15)
        void shouldGetTrendingSeriesWeek() {
            webTestClient.get().uri("/api/tmdb/trending/series/week")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test @Order(16)
        void shouldGetTrendingAllDay() {
            webTestClient.get().uri("/api/tmdb/trending/all/day")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test @Order(17)
        void shouldGetTrendingAllWeek() {
            webTestClient.get().uri("/api/tmdb/trending/all/week")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }
    }

    @Nested
    @DisplayName("TMDB Additional Endpoints")
    class AdditionalEndpoints {
        @Test @Order(18)
        void shouldGetUpcomingMovies() {
            webTestClient.get().uri("/api/tmdb/upcoming/movies")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test @Order(19)
        void shouldGetTopRatedMovies() {
            webTestClient.get().uri("/api/tmdb/top-rated/movies")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test @Order(20)
        void shouldGetTopRatedSeries() {
            webTestClient.get().uri("/api/tmdb/top-rated/series")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }
    }


}
