package com.mesurpreenda.api.data.testes;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RandomFavoriteEndpointsTests {

    @Autowired
    private WebTestClient webTestClient;

    private String userId;

    @BeforeAll
    void setUp() {
        createTestUser();
        addSampleContent();
    }

    private void createTestUser() {
        webTestClient.post().uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{ \"name\": \"Teste\", \"email\": \"teste@example.com\" }")
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").value(id -> userId = id.toString());
    }

    private void addSampleContent() {
        addMovieToFavorites("{ \"id\": \"100\", \"title\": \"Filme A\" }");
        addMovieToFavorites("{ \"id\": \"200\", \"title\": \"Filme B\" }");
        addSeriesToFavorites("{ \"id\": \"300\", \"title\": \"Series X\" }");
        addSeriesToFavorites("{ \"id\": \"400\", \"title\": \"Series Y\" }");
    }

    private void addMovieToFavorites(String body) {
        webTestClient.post().uri(uriBuilder ->
                        uriBuilder.path("/api/favorite/movie/{id}")
                                .queryParam("isMovie", true)
                                .build(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange().expectStatus().isOk();
    }

    private void addSeriesToFavorites(String body) {
        webTestClient.post().uri(uriBuilder ->
                        uriBuilder.path("/api/favorite/series/{id}")
                                .queryParam("isMovie", false)
                                .build(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange().expectStatus().isOk();
    }

    @Nested
    @DisplayName("Sortear Favoritos")
    class RandomFavorites {

        @Test
        @DisplayName("Deve retornar conteúdo aleatório de qualquer tipo")
        void getRandomContent_success() {
            webTestClient.get().uri("/api/random/{id}", userId)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.id").isNotEmpty();
        }

        @Test
        @DisplayName("Deve retornar filme aleatório dos favoritos")
        void getRandomMovie_success() {
            webTestClient.get().uri("/api/random/movie/{id}", userId)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.id").isNotEmpty()
                    .jsonPath("$.title").isNotEmpty();
        }

        @Test
        @DisplayName("Deve retornar série aleatória dos favoritos")
        void getRandomSeries_success() {
            webTestClient.get().uri("/api/random/series/{id}", userId)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.id").isNotEmpty()
                    .jsonPath("$.title").isNotEmpty();
        }
    }
}
