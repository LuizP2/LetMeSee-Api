package com.mesurpreenda.api.data.testes;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserEndpointsTest {

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

    @Nested
    @DisplayName("Gerenciar Usuários")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class ManageUsers {
        
        @Test
        @Order(1)
        @DisplayName("Deve criar um novo usuário")
        void createUser_success() {
            webTestClient.post().uri("/api/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("""
                            {
                                "name": "Novo Usuário",
                                "email": "novo@example.com"
                            }
                            """)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody()
                    .jsonPath("$.id").isNotEmpty()
                    .jsonPath("$.name").isEqualTo("Novo Usuário")
                    .jsonPath("$.email").isEqualTo("novo@example.com");
        }

        @Test
        @Order(2)
        @DisplayName("Deve listar todos os usuários")
        void getAllUsers_success() {
            webTestClient.get().uri("/api/users")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBodyList(Object.class)
                    .value(list -> assertThat(list).hasSizeGreaterThanOrEqualTo(1));
        }

        @Test
        @Order(3)
        @DisplayName("Deve retornar usuário por ID")
        void getUserById_success() {
            webTestClient.get().uri("/api/users/{id}", userId)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(userId);
        }

        @Test
        @Order(4)
        @DisplayName("Deve atualizar dados do usuário")
        void updateUser_success() {
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
        @DisplayName("Deve excluir usuário existente")
        void deleteUser_success() {
            webTestClient.delete().uri("/api/users/{id}", userId)
                    .exchange()
                    .expectStatus().isNoContent();
        }
    }
}
