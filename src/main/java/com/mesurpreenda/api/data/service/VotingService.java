package com.mesurpreenda.api.data.service;

import com.mesurpreenda.api.data.entity.*;
import com.mesurpreenda.api.data.repository.*;
import com.mesurpreenda.api.domain.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class VotingService {

    @Autowired
    private VotingRoomRepository votingRoomRepository;
    
    @Autowired
    private VotingParticipantRepository votingParticipantRepository;
    
    @Autowired
    private VotingSessionRepository votingSessionRepository;
    
    @Autowired
    private IndividualVoteRepository individualVoteRepository;
    
    @Autowired
    private WatchListService watchListService;

    public VotingRoom createVotingRoom(String leaderId, String watchListId) {
        // Validar se usuário pode acessar a watchlist
        if (!canUserAccessWatchList(leaderId, watchListId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User cannot access watchlist");
        }
        
        // Verificar se usuário já está em sala
        if (isUserInAnyRoom(leaderId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already in a voting room");
        }
        
        VotingRoom room = new VotingRoom();
        room.setRoomCode(generateRoomCode());
        room.setWatchlistId(watchListId);
        room.setLeaderId(leaderId);
        room.setStatus(VotingRoom.VotingStatus.WAITING);
        
        VotingRoom savedRoom = votingRoomRepository.save(room);
        
        // Adicionar líder como participante
        addParticipant(savedRoom.getRoomCode(), leaderId);
        
        return savedRoom;
    }

    public boolean canUserAccessWatchList(String userId, String watchListId) {
        try {
            Optional<WatchList> watchList = watchListService.getWatchListById(watchListId);
            if (watchList.isEmpty()) {
                return false;
            }
            
            WatchList wl = watchList.get();
            // Verificar se é owner ou colaborador
            return wl.getCreatorId().equals(userId) || 
                   wl.getCollaborators().stream().anyMatch(u -> u.getId().equals(userId));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserInAnyRoom(String userId) {
        return votingRoomRepository.findActiveRoomByParticipant(userId).isPresent();
    }

    public void addParticipant(String roomCode, String userId) {
        VotingRoom room = votingRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
        
        // Verificar limite de participantes (10)
        long participantCount = votingParticipantRepository.countByRoomCode(roomCode);
        if (participantCount >= 10) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Room is full (max 10 participants)");
        }
        
        // Verificar se usuário já está na sala
        if (votingParticipantRepository.findByRoomCodeAndUserId(roomCode, userId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already in this room");
        }
        
        // Verificar se usuário já está em outra sala
        if (isUserInAnyRoom(userId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already in another room");
        }
        
        VotingParticipant participant = new VotingParticipant();
        participant.setVotingRoom(room);
        participant.setUserId(userId);
        participant.setIsConnected(true);
        
        votingParticipantRepository.save(participant);
    }

    public VotingContentDTO startNewVoting(String roomCode, String userId) {
        VotingRoom room = votingRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
        
        // Verificar se é o líder
        if (!room.getLeaderId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the leader can start voting");
        }
        
        // Buscar conteúdo aleatório da watchlist
        Object randomContent = watchListService.getRandomFromWatchList(room.getWatchlistId());
        
        VotingContentDTO contentDTO = convertToVotingContentDTO(randomContent);
        
        // Atualizar sala
        room.setCurrentContentId(contentDTO.getId());
        room.setCurrentContentType(contentDTO.getType().equals("MOVIE") ? 
                VotingRoom.ContentType.MOVIE : VotingRoom.ContentType.SERIES);
        room.setStatus(VotingRoom.VotingStatus.VOTING);
        room.setVoteEndTime(LocalDateTime.now().plusSeconds(10));
        
        votingRoomRepository.save(room);
        
        // Criar sessão de votação
        VotingSession session = new VotingSession();
        session.setVotingRoom(room);
        session.setContentId(contentDTO.getId());
        session.setContentType(room.getCurrentContentType());
        votingSessionRepository.save(session);
        
        return contentDTO;
    }

    public VotingResultDTO processVote(String roomCode, String userId, String vote) {
        VotingRoom room = votingRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
        
        if (room.getStatus() != VotingRoom.VotingStatus.VOTING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room is not in voting state");
        }
        
        // Verificar se usuário está na sala
        votingParticipantRepository.findByRoomCodeAndUserId(roomCode, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not in this room"));
        
        // Buscar sessão atual
        VotingSession session = votingSessionRepository.findCurrentSessionByRoomId(room.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No active voting session"));
        
        // Verificar se usuário já votou
        if (individualVoteRepository.existsByVotingSessionIdAndUserId(session.getId(), userId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User has already voted");
        }
        
        // Salvar voto individual
        IndividualVote individualVote = new IndividualVote();
        individualVote.setVotingSession(session);
        individualVote.setUserId(userId);
        individualVote.setVote(vote.equals("YES") ? IndividualVote.Vote.YES : IndividualVote.Vote.NO);
        individualVoteRepository.save(individualVote);
        
        // Calcular resultado atual
        return calculateVotingResult(room, session);
    }

    private VotingResultDTO calculateVotingResult(VotingRoom room, VotingSession session) {
        long yesVotes = individualVoteRepository.countYesVotesBySession(session.getId());
        long noVotes = individualVoteRepository.countNoVotesBySession(session.getId());
        
        return VotingResultDTO.builder()
                .roomCode(room.getRoomCode())
                .contentId(session.getContentId())
                .contentType(session.getContentType().name())
                .yesVotes((int) yesVotes)
                .noVotes((int) noVotes)
                .totalVotes((int) (yesVotes + noVotes))
                .accepted(yesVotes > noVotes)
                .build();
    }

    private VotingContentDTO convertToVotingContentDTO(Object content) {
        // Conversão baseada nos campos disponíveis nas entidades Movie e Series
        if (content instanceof Movie) {
            Movie movie = (Movie) content;
            return VotingContentDTO.builder()
                    .id(movie.getId())
                    .title(movie.getTitle())
                    .overview("") // Campo não disponível na entidade atual
                    .posterPath("") // Campo não disponível na entidade atual
                    .type("MOVIE")
                    .releaseDate(movie.getYear()) // Usando year como releaseDate
                    .genres(movie.getGenre() != null ? new String[]{movie.getGenre()} : new String[0])
                    .build();
        } else if (content instanceof Series) {
            Series series = (Series) content;
            return VotingContentDTO.builder()
                    .id(series.getId())
                    .title(series.getTitle())
                    .overview("") // Campo não disponível na entidade atual
                    .posterPath("") // Campo não disponível na entidade atual
                    .type("SERIES")
                    .releaseDate("") // Campo não disponível na entidade atual
                    .genres(new String[0]) // Lista de genres seria convertida separadamente
                    .build();
        }
        
        throw new IllegalArgumentException("Unknown content type");
    }

    private String generateRoomCode() {
        String code;
        do {
            code = String.format("%04d", new Random().nextInt(10000));
        } while (votingRoomRepository.existsByRoomCode(code));
        return code;
    }

    public List<VotingRoom> processExpiredVotings() {
        return votingRoomRepository.findExpiredVotingRooms(LocalDateTime.now());
    }

    public VotingRoomDTO getRoomInfo(String roomCode) {
        VotingRoom room = votingRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
        
        List<VotingParticipant> participants = votingParticipantRepository.findByRoomCode(roomCode);
        
        return VotingRoomDTO.builder()
                .id(room.getId())
                .roomCode(room.getRoomCode())
                .watchlistId(room.getWatchlistId())
                .leaderId(room.getLeaderId())
                .status(room.getStatus().name())
                .createdAt(room.getCreatedAt())
                .voteEndTime(room.getVoteEndTime())
                .totalParticipants(participants.size())
                .connectedParticipants((int) participants.stream().filter(VotingParticipant::getIsConnected).count())
                .build();
    }

    public Page<VotingHistoryDTO> getVotingHistory(String userId, Pageable pageable) {
        return votingSessionRepository.findVotingHistoryByUser(userId, pageable)
                .map(session -> VotingHistoryDTO.builder()
                        .sessionId(session.getId())
                        .roomCode(session.getVotingRoom().getRoomCode())
                        .content(convertToVotingContentDTO(getContentById(session.getContentId(), session.getContentType())))
                        .yesVotes(session.getYesVotes())
                        .noVotes(session.getNoVotes())
                        .result(session.getResult() != null ? session.getResult().name() : null)
                        .votedAt(session.getVotedAt())
                        .build());
    }

    private Object getContentById(Long contentId, VotingRoom.ContentType contentType) {
        // Implementação para buscar filme ou série por ID
        // Esta seria uma integração com os repositórios de Movie/Series existentes
        return null; // Placeholder
    }
} 