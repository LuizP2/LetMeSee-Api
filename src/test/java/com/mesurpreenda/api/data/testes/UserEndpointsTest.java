package com.mesurpreenda.api.data.testes;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserEndpointsTest {

    @Autowired
    private WebTestClient webTestClient;

    private String userId;

    @Test
    @Order(1)
    void shouldCreateUser() {
        webTestClient.post().uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "name": "Vitola",
                            "email": "euamoavitola@example.com"
                        }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").value(id -> userId = id.toString());
        Assertions.assertNotNull(userId, "userId should have been set");
    }

    @Test
    @Order(2)
    void shouldListAllUsers() {
        webTestClient.get().uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Object.class)
                .value(list -> assertThat(list).hasSizeGreaterThanOrEqualTo(1));
    }

    @Test
    @Order(3)
    void shouldGetUserById() {
        Assertions.assertNotNull(userId, "userId must not be null");
        webTestClient.get().uri("/api/users/{id}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(userId);
    }

    @Test
    @Order(4)
    void shouldUpdateUser() {
        Assertions.assertNotNull(userId, "userId must not be null");
        webTestClient.put().uri("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "name": "Vitola Updated",
                            "email": "vitola.updated@example.com"
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Vitola Updated")
                .jsonPath("$.email").isEqualTo("vitola.updated@example.com");
    }

    @Test
    @Order(5)
    void shouldDeleteUser() {
        Assertions.assertNotNull(userId, "userId must not be null");
        webTestClient.delete().uri("/api/users/{id}", userId)
                .exchange()
                .expectStatus().isNoContent();
    }
}
