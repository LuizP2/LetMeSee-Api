package com.mesurpreenda.api.data.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FavoritesEndpointsTest{
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldGetFavoritesByUserId() {
        webTestClient.get().uri("/api/favorite/{id}", "u0000003-0000-0000-0000-000000000003")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldRemoveFavoriteForUser() {
        webTestClient.delete().uri(uriBuilder -> uriBuilder
                        .path("/api/favorite/{id}")
                        .queryParam("contentId", "11111111-1111-1111-1111-111111111111")
                        .queryParam("isMovie", true)
                        .build("u0000002-0000-0000-0000-000000000002"))
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    void shouldAddFavoriteForUser() {
        webTestClient.post().uri("/api/favorite/{id}", "u0000003-0000-0000-0000-000000000003")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "id": "11111111-1111-1111-1111-111111111222",
                            "title": "Test Movie",
                            "description": "Test Description"
                        }
                        """)
                .exchange()
                .expectStatus().isOk();
    }

}

