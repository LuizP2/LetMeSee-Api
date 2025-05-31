package com.mesurpreenda.api.data.service;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserEndpointsTest {

    @Autowired
    private WebTestClient webTestClient;

    private static String userId;


    @Test
    @Order(2)
    void shouldListAllUsers() {
        webTestClient.get().uri("/api/users")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(1)
    void shouldCreateUser() {
        webTestClient.post().uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "name": "Vitola",
                            "email": "EuamoaVitola@gmail.com"
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
    @Order(3)
    void shouldGetUserById() {
        webTestClient.get().uri("/api/users/{id}", userId)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(4)
    void shouldUpdateUser() {
        webTestClient.put().uri("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "name": "Vitola Updated",
                            "email": "EuamoaVitola.Updated@gmail.com"
                        }
                        """)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(5)
    void shouldDeleteUser() {
        webTestClient.delete().uri("/api/users/{id}", userId)
                .exchange()
                .expectStatus().isNoContent();
    }
}
