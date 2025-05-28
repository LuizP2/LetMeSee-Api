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
class UserEndpointsTest{
    @Autowired
    private WebTestClient webTestClient;


    @Test
    void shouldListAllUsers() {
        webTestClient.get().uri("/api/users")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldCreateUser() {
        webTestClient.post().uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "id": "u0000002-0000-0000-0000-000000000002",
                            "name": "John Doe",
                            "email": "john.doe@example.com"
                        }
                        """)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void shouldGetUserById() {
        webTestClient.get().uri("/api/users/{id}", "u0000002-0000-0000-0000-000000000002")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldUpdateUser() {
        webTestClient.put().uri("/api/users/{id}", "u0000002-0000-0000-0000-000000000002")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "id": "u0000002-0000-0000-0000-000000000003",
                            "name": "John Updated",
                            "email": "john.updated@example.com"
                        }
                        """)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldDeleteUser() {
        webTestClient.delete().uri("/api/users/{id}", "u0000002-0000-0000-0000-000000000002")
                .exchange()
                .expectStatus().isNoContent();
    }
}
