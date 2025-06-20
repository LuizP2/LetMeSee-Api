package com.mesurpreenda.api.data.testes;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FavoritesEndpointsTest {

    @Autowired
    private WebTestClient webTestClient;

    private String userId;

    @BeforeAll
    void setUp() {
        createTestUser();
    }

    private void createTestUser() {
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
                .jsonPath("$.id").value(id -> userId = id.toString());
    }

    @Nested
    @DisplayName("Gerenciar Favoritos")
    class ManageFavorites {

        @Test
        @DisplayName("Deve adicionar filme aos favoritos")
        void addFavoriteMovie_success() {
            webTestClient.post().uri(uriBuilder -> uriBuilder
                            .path("/api/favorite/movie/{id}")
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
        @DisplayName("Deve adicionar série aos favoritos")
        void addFavoriteSeries_success() {
            webTestClient.post().uri(uriBuilder -> uriBuilder
                            .path("/api/favorite/series/{id}")
                            .queryParam("isMovie", false)
                            .build(userId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("""
                            {
                                "id": "550",
                                "title": "Test series"
                            }
                            """)
                    .exchange()
                    .expectStatus().isOk();
        }

        @Test
        @DisplayName("Deve listar favoritos do usuário")
        void getFavorites_success() {
            webTestClient.get().uri("/api/favorite/{id}", userId)
                    .exchange()
                    .expectStatus().isOk();
        }

        @Test
        @DisplayName("Deve remover item dos favoritos")
        void removeFavorite_success() {
            webTestClient.delete().uri(uriBuilder -> uriBuilder
                            .path("/api/favorite/{id}")
                            .queryParam("contentId", "550")
                            .queryParam("isMovie", true)
                            .build(userId))
                    .exchange()
                    .expectStatus().isOk();
        }
    }
}
