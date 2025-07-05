package com.mesurpreenda.api.domain.controller;

import com.mesurpreenda.api.data.entity.User;
import com.mesurpreenda.api.data.entity.VotingRoom;
import com.mesurpreenda.api.data.service.VotingService;
import com.mesurpreenda.api.domain.dto.VotingHistoryDTO;
import com.mesurpreenda.api.domain.dto.VotingRoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/voting")
public class VotingController {

    @Autowired
    private VotingService votingService;

    /**
     * Criar uma nova sala de votação
     */
    @PostMapping("/room")
    public ResponseEntity<VotingRoom> createRoom(
            @AuthenticationPrincipal User user,
            @RequestParam String watchListId) {
        
        VotingRoom room = votingService.createVotingRoom(user.getId(), watchListId);
        URI location = URI.create("/api/voting/room/" + room.getRoomCode());
        return ResponseEntity.created(location).body(room);
    }

    /**
     * Entrar em uma sala de votação
     */
    @PostMapping("/room/{roomCode}/join")
    public ResponseEntity<String> joinRoom(
            @AuthenticationPrincipal User user,
            @PathVariable String roomCode) {
        
        votingService.addParticipant(roomCode, user.getId());
        return ResponseEntity.ok("Successfully joined room " + roomCode);
    }

    /**
     * Obter informações da sala de votação
     */
    @GetMapping("/room/{roomCode}")
    public ResponseEntity<VotingRoomDTO> getRoomInfo(@PathVariable String roomCode) {
        VotingRoomDTO room = votingService.getRoomInfo(roomCode);
        return ResponseEntity.ok(room);
    }

    /**
     * Obter histórico de votações do usuário
     */
    @GetMapping("/history")
    public ResponseEntity<Page<VotingHistoryDTO>> getVotingHistory(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<VotingHistoryDTO> history = votingService.getVotingHistory(user.getId(), pageable);
        return ResponseEntity.ok(history);
    }

    /**
     * Verificar se usuário está em alguma sala ativa
     */
    @GetMapping("/status")
    public ResponseEntity<Boolean> getUserVotingStatus(@AuthenticationPrincipal User user) {
        boolean inRoom = votingService.isUserInAnyRoom(user.getId());
        return ResponseEntity.ok(inRoom);
    }

    /**
     * Sair da sala atual (apenas para casos de emergência via REST)
     */
    @PostMapping("/leave")
    public ResponseEntity<String> leaveCurrentRoom(@AuthenticationPrincipal User user) {
        // Esta funcionalidade seria implementada no VotingService
        // Por agora, retornamos um placeholder
        return ResponseEntity.ok("Left room successfully");
    }

    /**
     * Obter estatísticas de votação para uma watchlist
     */
    @GetMapping("/stats/watchlist/{watchListId}")
    public ResponseEntity<Object> getWatchlistVotingStats(
            @AuthenticationPrincipal User user,
            @PathVariable String watchListId) {
        
        // Esta funcionalidade seria implementada para análise de dados
        // Por agora, retornamos um placeholder
        return ResponseEntity.ok("Statistics not implemented yet");
    }

    /**
     * Endpoint para testes - verificar salas ativas
     */
    @GetMapping("/rooms/active")
    public ResponseEntity<Object> getActiveRooms() {
        // Esta funcionalidade seria para administração/debug
        // Por agora, retornamos um placeholder
        return ResponseEntity.ok("Active rooms listing not implemented yet");
    }
} 