package com.mesurpreenda.api.data.testes;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TmdbEndpointsTest {

    @Autowired
    private WebTestClient webTestClient;

    @Nested
    @DisplayName("Endpoints de Status")
    class StatusEndpoints {
        @Test
        @DisplayName("Deve retornar status da API")
        void getApiStatus_success() {
            webTestClient.get().uri("/")
                    .exchange()
                    .expectStatus().isOk();
        }
    }

    @Nested
    @DisplayName("Endpoints de Detalhes")
    class DetailEndpoints {
        @Test
        @DisplayName("Deve retornar detalhes do filme por ID")
        void getMovieById_success() {
            webTestClient.get().uri("/api/tmdb/movie/{id}", 550)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(550);
        }

        @Test
        @DisplayName("Deve retornar detalhes da série por ID")
        void getSeriesById_success() {
            webTestClient.get().uri("/api/tmdb/series/{id}", 100)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(100);
        }
    }

    @Nested
    @DisplayName("Endpoints de Busca")
    class SearchEndpoints {
        @Test
        @DisplayName("Deve buscar em todo conteúdo")
        void searchAll_success() {
            webTestClient.get().uri(uriBuilder -> uriBuilder
                            .path("/api/tmdb/search")
                            .queryParam("query", "Matrix")
                            .build())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test
        @DisplayName("Deve buscar apenas filmes")
        void searchMovies_success() {
            webTestClient.get().uri(uriBuilder -> uriBuilder
                            .path("/api/tmdb/search/movie")
                            .queryParam("query", "Matrix")
                            .build())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test
        @DisplayName("Deve buscar apenas séries")
        void searchSeries_success() {
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
    @DisplayName("Endpoints de Descoberta")
    class DiscoveryEndpoints {
        @Test
        @DisplayName("Deve descobrir filmes")
        void discoverMovies_success() {
            webTestClient.get().uri("/api/tmdb/discover/movie")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test
        @DisplayName("Deve descobrir séries")
        void discoverSeries_success() {
            webTestClient.get().uri("/api/tmdb/discover/series")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }
    }

    @Nested
    @DisplayName("Endpoints de Tendências")
    class TrendingEndpoints {
        @Test
        @DisplayName("Deve retornar filmes em tendência do dia")
        void getTrendingMoviesDay_success() {
            webTestClient.get().uri("/api/tmdb/trending/movie/day")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test
        @DisplayName("Deve retornar séries em tendência do dia")
        void getTrendingSeriesDay_success() {
            webTestClient.get().uri("/api/tmdb/trending/series/day")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test
        @DisplayName("Deve retornar filmes em tendência da semana")
        void getTrendingMoviesWeek_success() {
            webTestClient.get().uri("/api/tmdb/trending/movie/week")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test
        @DisplayName("Deve retornar séries em tendência da semana")
        void getTrendingSeriesWeek_success() {
            webTestClient.get().uri("/api/tmdb/trending/series/week")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test
        @DisplayName("Deve retornar todo conteúdo em tendência do dia")
        void getTrendingAllDay_success() {
            webTestClient.get().uri("/api/tmdb/trending/all/day")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test
        @DisplayName("Deve retornar todo conteúdo em tendência da semana")
        void getTrendingAllWeek_success() {
            webTestClient.get().uri("/api/tmdb/trending/all/week")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }
    }

    @Nested
    @DisplayName("Endpoints Adicionais")
    class AdditionalEndpoints {
        @Test
        @DisplayName("Deve retornar filmes em lançamento")
        void getUpcomingMovies_success() {
            webTestClient.get().uri("/api/tmdb/upcoming/movies")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test
        @DisplayName("Deve retornar filmes mais bem avaliados")
        void getTopRatedMovies_success() {
            webTestClient.get().uri("/api/tmdb/top-rated/movies")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }

        @Test
        @DisplayName("Deve retornar séries mais bem avaliadas")
        void getTopRatedSeries_success() {
            webTestClient.get().uri("/api/tmdb/top-rated/series")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.results").isArray();
        }
    }
}
