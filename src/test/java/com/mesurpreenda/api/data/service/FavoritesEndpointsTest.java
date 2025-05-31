package com.mesurpreenda.api.data.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FavoritesEndpointsTest {

    @Autowired
    private WebTestClient webTestClient;

    private static String userId;

    @Test
    @Order(0)
    void shouldCreateUser() {
        webTestClient.post().uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "name": "Test User",
                            "email": "testuser@example.com"
                        }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").value(id -> {
                    userId = id.toString();
                });
    }

    @Test
    @Order(1)
    void shouldAddFavoriteForUser() {
        webTestClient.post().uri(uriBuilder -> uriBuilder
                        .path("/api/favorite/{id}")
                        .queryParam("isMovie", true)
                        .build(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "id": "550",
                            "title": "Test Movie"
                        }
                        """)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(2)
    void shouldGetFavoritesByUserId() {
        webTestClient.get().uri("/api/favorite/{id}", userId)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(3)
    void shouldRemoveFavoriteForUser() {
        webTestClient.delete().uri(uriBuilder -> uriBuilder
                        .path("/api/favorite/{id}")
                        .queryParam("contentId", "550")
                        .queryParam("isMovie", true)
                        .build(userId))
                .exchange()
                .expectStatus().isOk();
    }
}
