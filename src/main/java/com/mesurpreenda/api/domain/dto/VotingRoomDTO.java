package com.mesurpreenda.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotingRoomDTO {
    
    private String id;
    private String roomCode;
    private String watchlistId;
    private String watchlistTitle;
    private String leaderId;
    private String leaderName;
    private String status;
    private VotingContentDTO currentContent;
    private LocalDateTime voteEndTime;
    private LocalDateTime createdAt;
    private List<VotingParticipantDTO> participants;
    private Integer totalParticipants;
    private Integer connectedParticipants;
} 