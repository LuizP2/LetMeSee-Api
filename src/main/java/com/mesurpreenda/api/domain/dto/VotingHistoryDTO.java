package com.mesurpreenda.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotingHistoryDTO {
    
    private String sessionId;
    private String roomCode;
    private String watchlistTitle;
    private VotingContentDTO content;
    private String userVote; // "YES", "NO" ou null se não votou
    private Integer yesVotes;
    private Integer noVotes;
    private String result; // "ACCEPTED" ou "REJECTED"
    private LocalDateTime votedAt;
} 