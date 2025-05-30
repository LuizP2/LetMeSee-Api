package com.mesurpreenda.api.data.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FavoritesEndpointsTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(2)
    void shouldGetFavoritesByUserId() {
        webTestClient.get().uri("/api/favorite/{id}", "12c874c8-bd79-4171-880f-eae16baf9428")
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
                        .build("12c874c8-bd79-4171-880f-eae16baf9428"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(1)
    void shouldAddFavoriteForUser() {
        webTestClient.post().uri(uriBuilder -> uriBuilder
                        .path("/api/favorite/{id}")
                        .queryParam("isMovie", true)
                        .build("12c874c8-bd79-4171-880f-eae16baf9428"))
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

}

