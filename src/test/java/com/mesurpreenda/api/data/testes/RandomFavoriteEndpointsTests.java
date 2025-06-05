package com.mesurpreenda.api.data.testes;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RandomFavoriteEndpointsTests {

    @Autowired
    private WebTestClient webTestClient;

    private String userId;

    @Test
    @Order(0)
    void shouldCreateUserAndAddSomeFavorites() {
        CreateAnTestUser();
        AddAnMovieToTheFavoritesList("{ \"id\": \"100\", \"title\": \"Filme A\" }");
        AddAnMovieToTheFavoritesList("{ \"id\": \"200\", \"title\": \"Filme B\" }");
        AddAnSeriesToTheFavoritesList("{ \"id\": \"300\", \"title\": \"Series X\" }");
        AddAnSeriesToTheFavoritesList("{ \"id\": \"400\", \"title\": \"Series y\" }");
    }


    @Test
    @Order(1)
    void shouldReturnRandomFavoriteAnyType() {
        webTestClient.get().uri("/api/random/{id}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty();
    }

    @Test
    @Order(2)
    void shouldReturnRandomFavoriteMovie() {
        webTestClient.get().uri("/api/random/movie/{id}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.title").isNotEmpty();
    }

    @Test
    @Order(3)
    void shouldReturnRandomFavoriteSeries() {
        webTestClient.get().uri("/api/random/series/{id}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.title").isNotEmpty();
    }

    private void AddAnMovieToTheFavoritesList(String body) {
        webTestClient.post().uri(uriBuilder ->
                        uriBuilder.path("/api/favorite/movie/{id}")
                                .queryParam("isMovie", true)
                                .build(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange().expectStatus().isOk();
    }

    private void AddAnSeriesToTheFavoritesList(String body) {
        webTestClient.post().uri(uriBuilder ->
                        uriBuilder.path("/api/favorite/series/{id}")
                                .queryParam("isMovie", false)
                                .build(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange().expectStatus().isOk();
    }


    private void CreateAnTestUser() {
        webTestClient.post().uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{ \"name\": \"Teste\", \"email\": \"teste@example.com\" }")
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").value(id -> userId = id.toString());
    }
}
