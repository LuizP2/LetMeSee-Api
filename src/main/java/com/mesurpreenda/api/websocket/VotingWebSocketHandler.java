package com.mesurpreenda.api.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mesurpreenda.api.data.entity.VotingRoom;
import com.mesurpreenda.api.data.service.VotingService;
import com.mesurpreenda.api.domain.dto.VotingContentDTO;
import com.mesurpreenda.api.domain.dto.VotingMessageDTO;
import com.mesurpreenda.api.domain.dto.VotingResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VotingWebSocketHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(VotingWebSocketHandler.class);

    @Autowired
    private VotingService votingService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // Mapear salas para sessões WebSocket
    private final Map<String, Set<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();
    
    // Mapear sessão para usuário e sala
    private final Map<String, String> sessionToUser = new ConcurrentHashMap<>();
    private final Map<String, String> userToRoom = new ConcurrentHashMap<>();
    private final Map<String, String> sessionToRoom = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomCode = extractRoomCodeFromSession(session);
        String userId = extractUserIdFromSession(session);
        
        log.info("WebSocket connection established - User: {}, Room: {}", userId, roomCode);
        
        try {
            // Validar se usuário pode entrar na sala
            if (!canUserJoinRoom(userId, roomCode)) {
                session.close(CloseStatus.POLICY_VIOLATION.withReason("User cannot join room"));
                return;
            }
            
            // Verificar se usuário já está em outra sala
            if (userToRoom.containsKey(userId)) {
                String currentRoom = userToRoom.get(userId);
                if (!currentRoom.equals(roomCode)) {
                    session.close(CloseStatus.POLICY_VIOLATION.withReason("User already in another room"));
                    return;
                }
            }
            
            // Adicionar à sala
            roomSessions.computeIfAbsent(roomCode, k -> ConcurrentHashMap.newKeySet()).add(session);
            sessionToUser.put(session.getId(), userId);
            sessionToRoom.put(session.getId(), roomCode);
            userToRoom.put(userId, roomCode);
            
            // Atualizar status de conexão do participante
            updateParticipantConnection(roomCode, userId, true);
            
            // Notificar outros participantes sobre entrada
            broadcastUserJoined(roomCode, userId);
            
            // Enviar estado atual da sala
            sendCurrentRoomState(session, roomCode);
            
        } catch (Exception e) {
            log.error("Error establishing WebSocket connection", e);
            session.close(CloseStatus.SERVER_ERROR.withReason("Internal server error"));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userId = sessionToUser.get(session.getId());
        String roomCode = sessionToRoom.get(session.getId());
        
        try {
            VotingMessageDTO messageDTO = objectMapper.readValue(message.getPayload(), VotingMessageDTO.class);
            
            switch (messageDTO.getType()) {
                case VOTE_CAST:
                    handleVote(roomCode, userId, messageDTO.getVote());
                    break;
                case NEW_VOTING:
                    handleStartVoting(roomCode, userId);
                    break;
                case RECONNECTION_POPUP:
                    handleReconnection(session, roomCode, userId);
                    break;
                default:
                    log.warn("Unknown message type: {}", messageDTO.getType());
            }
            
        } catch (Exception e) {
            log.error("Error handling WebSocket message", e);
            sendErrorMessage(session, "Error processing message: " + e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = sessionToUser.remove(session.getId());
        String roomCode = sessionToRoom.remove(session.getId());
        
        if (roomCode != null && userId != null) {
            log.info("WebSocket connection closed - User: {}, Room: {}", userId, roomCode);
            
            // Remover da sala
            Set<WebSocketSession> sessions = roomSessions.get(roomCode);
            if (sessions != null) {
                sessions.remove(session);
                if (sessions.isEmpty()) {
                    roomSessions.remove(roomCode);
                }
            }
            
            userToRoom.remove(userId);
            
            // Atualizar status de conexão
            updateParticipantConnection(roomCode, userId, false);
            
            // Notificar outros participantes sobre saída
            broadcastUserLeft(roomCode, userId);
        }
    }

    private void handleVote(String roomCode, String userId, String vote) {
        try {
            VotingResultDTO result = votingService.processVote(roomCode, userId, vote);
            
            // Broadcast atualização dos votos
            broadcastVoteUpdate(roomCode, result);
            
        } catch (Exception e) {
            log.error("Error processing vote", e);
            broadcastError(roomCode, "Error processing vote: " + e.getMessage());
        }
    }

    private void handleStartVoting(String roomCode, String userId) {
        try {
            VotingContentDTO content = votingService.startNewVoting(roomCode, userId);
            broadcastNewVoting(roomCode, content);
            
        } catch (Exception e) {
            log.error("Error starting voting", e);
            broadcastError(roomCode, "Error starting voting: " + e.getMessage());
        }
    }

    private void handleReconnection(WebSocketSession session, String roomCode, String userId) {
        try {
            sendCurrentRoomState(session, roomCode);
        } catch (Exception e) {
            log.error("Error handling reconnection", e);
        }
    }

    @Scheduled(fixedDelay = 1000) // Verificar a cada segundo
    public void checkVotingTimeouts() {
        try {
            List<VotingRoom> expiredRooms = votingService.processExpiredVotings();
            for (VotingRoom room : expiredRooms) {
                processVotingTimeout(room);
            }
        } catch (Exception e) {
            log.error("Error checking voting timeouts", e);
        }
    }

    private void processVotingTimeout(VotingRoom room) {
        // Lógica para processar timeout será implementada no service
        // Por agora, apenas notificar que o tempo acabou
        broadcastVotingTimeout(room.getRoomCode());
    }

    // Métodos de broadcast
    private void broadcastUserJoined(String roomCode, String userId) {
        VotingMessageDTO message = VotingMessageDTO.builder()
                .type(VotingMessageDTO.MessageType.USER_JOINED)
                .roomCode(roomCode)
                .userId(userId)
                .message(userId + " entrou na sala")
                .timestamp(System.currentTimeMillis())
                .build();
        
        broadcastToRoom(roomCode, message, null);
    }

    private void broadcastUserLeft(String roomCode, String userId) {
        VotingMessageDTO message = VotingMessageDTO.builder()
                .type(VotingMessageDTO.MessageType.USER_LEFT)
                .roomCode(roomCode)
                .userId(userId)
                .message(userId + " saiu da sala")
                .timestamp(System.currentTimeMillis())
                .build();
        
        broadcastToRoom(roomCode, message, null);
    }

    private void broadcastNewVoting(String roomCode, VotingContentDTO content) {
        VotingMessageDTO message = VotingMessageDTO.builder()
                .type(VotingMessageDTO.MessageType.NEW_VOTING)
                .roomCode(roomCode)
                .content(content)
                .timer(10)
                .timestamp(System.currentTimeMillis())
                .build();
        
        broadcastToRoom(roomCode, message, null);
    }

    private void broadcastVoteUpdate(String roomCode, VotingResultDTO result) {
        VotingMessageDTO message = VotingMessageDTO.builder()
                .type(VotingMessageDTO.MessageType.VOTE_CAST)
                .roomCode(roomCode)
                .result(result)
                .timestamp(System.currentTimeMillis())
                .build();
        
        broadcastToRoom(roomCode, message, null);
    }

    private void broadcastVotingTimeout(String roomCode) {
        VotingMessageDTO message = VotingMessageDTO.builder()
                .type(VotingMessageDTO.MessageType.TIMER_UPDATE)
                .roomCode(roomCode)
                .timer(0)
                .message("Tempo esgotado!")
                .timestamp(System.currentTimeMillis())
                .build();
        
        broadcastToRoom(roomCode, message, null);
    }

    private void broadcastError(String roomCode, String errorMessage) {
        VotingMessageDTO message = VotingMessageDTO.builder()
                .type(VotingMessageDTO.MessageType.ERROR)
                .roomCode(roomCode)
                .message(errorMessage)
                .timestamp(System.currentTimeMillis())
                .build();
        
        broadcastToRoom(roomCode, message, null);
    }

    private void broadcastToRoom(String roomCode, VotingMessageDTO message, WebSocketSession excludeSession) {
        Set<WebSocketSession> sessions = roomSessions.get(roomCode);
        if (sessions != null) {
            String jsonMessage = convertToJson(message);
            sessions.forEach(session -> {
                if (session != excludeSession && session.isOpen()) {
                    try {
                        session.sendMessage(new TextMessage(jsonMessage));
                    } catch (IOException e) {
                        log.error("Error sending message to session: {}", session.getId(), e);
                    }
                }
            });
        }
    }

    private void sendCurrentRoomState(WebSocketSession session, String roomCode) {
        try {
            // Buscar estado atual da sala
            // Esta implementação será expandida conforme necessário
            VotingMessageDTO message = VotingMessageDTO.builder()
                    .type(VotingMessageDTO.MessageType.ROOM_STATE_UPDATE)
                    .roomCode(roomCode)
                    .message("Estado atual da sala")
                    .timestamp(System.currentTimeMillis())
                    .build();
            
            session.sendMessage(new TextMessage(convertToJson(message)));
        } catch (IOException e) {
            log.error("Error sending room state", e);
        }
    }

    private void sendErrorMessage(WebSocketSession session, String errorMessage) {
        try {
            VotingMessageDTO message = VotingMessageDTO.builder()
                    .type(VotingMessageDTO.MessageType.ERROR)
                    .message(errorMessage)
                    .timestamp(System.currentTimeMillis())
                    .build();
            
            session.sendMessage(new TextMessage(convertToJson(message)));
        } catch (IOException e) {
            log.error("Error sending error message", e);
        }
    }

    // Métodos utilitários
    private String extractRoomCodeFromSession(WebSocketSession session) {
        URI uri = session.getUri();
        if (uri != null) {
            String path = uri.getPath();
            // Extrair room code da URL /voting/{roomCode}
            String[] parts = path.split("/");
            if (parts.length >= 3) {
                return parts[2];
            }
        }
        return null;
    }

    private String extractUserIdFromSession(WebSocketSession session) {
        // Extrair userId dos headers ou query parameters
        // Por simplicidade, usando um header customizado
        List<String> userIdHeader = session.getHandshakeHeaders().get("X-User-Id");
        if (userIdHeader != null && !userIdHeader.isEmpty()) {
            return userIdHeader.get(0);
        }
        return null;
    }

    private boolean canUserJoinRoom(String userId, String roomCode) {
        try {
            // Verificar se sala existe e se usuário pode participar
            return votingService.getRoomInfo(roomCode) != null;
        } catch (Exception e) {
            return false;
        }
    }

    private void updateParticipantConnection(String roomCode, String userId, boolean connected) {
        // Esta funcionalidade seria implementada no VotingService
        // votingService.updateParticipantConnection(roomCode, userId, connected);
    }

    private String convertToJson(VotingMessageDTO message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            log.error("Error converting message to JSON", e);
            return "{}";
        }
    }
} 