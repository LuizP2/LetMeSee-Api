package com.mesurpreenda.api.config;

import com.mesurpreenda.api.websocket.VotingWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private VotingWebSocketHandler votingWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        registry.addHandler(votingWebSocketHandler, "/voting/*")
                .setAllowedOrigins("*") // Em produção, especificar os domínios permitidos
                .withSockJS(); // Suporte para SockJS como fallback
    }
} 