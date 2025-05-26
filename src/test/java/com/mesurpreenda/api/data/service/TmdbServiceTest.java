package com.mesurpreenda.api.data.service;

import com.mesurpreenda.api.domain.dto.TmdbResultDTO;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class TmdbServiceTest {
    private static MockWebServer mockWebServer;
    private static TmdbService tmdbService;

    @BeforeAll
    static void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseUrl = mockWebServer.url("/").toString();

        WebClient.Builder webClient = WebClient.builder()
                .baseUrl(baseUrl);

        tmdbService = new TmdbService(webClient);
        tmdbService.setApiKey("6b0f4064f385f7be11d8cc858b128478");
    }

    @AfterAll
    static void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void testGetMovieById() {
        Long movieId = 550L;
        String responseBody = "{ \"id\": 123, \"title\": \"Fake Movie\" }";
        mockWebServer.enqueue(new MockResponse()
                .setBody(responseBody)
                .addHeader("Content-Type", "application/json"));

        Mono<TmdbResultDTO> result = tmdbService.getMovieById(movieId);

        StepVerifier.create(result)
                .assertNext(dto -> {
                    assert dto != null;
                    assert dto.getId() == 550L;
                    assert dto.getTitle().equals("Clube da Luta");
                })
                .verifyComplete();
    }
}
