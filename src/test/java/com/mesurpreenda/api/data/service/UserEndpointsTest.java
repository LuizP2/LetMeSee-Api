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
class UserEndpointsTest {

    @Autowired
    private WebTestClient webTestClient;


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
                .expectStatus().isCreated();
    }

    @Test
    @Order(3)
    void shouldGetUserById() {
        webTestClient.get().uri("/api/users/{id}", "77b774ad-b4ce-423d-bde1-f684c3e0ca97")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(4)
    void shouldUpdateUser() {
        webTestClient.put().uri("/api/users/{id}", "6848dc02-c313-410e-8d3b-30f656375267")
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
        webTestClient.delete().uri("/api/users/{id}", "6848dc02-c313-410e-8d3b-30f656375267")
                .exchange()
                .expectStatus().isNoContent();
    }
}
