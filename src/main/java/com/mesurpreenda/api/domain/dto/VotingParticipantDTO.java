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
public class VotingParticipantDTO {
    
    private String id;
    private String userId;
    private String userName;
    private LocalDateTime joinedAt;
    private Boolean isConnected;
    private Boolean isLeader;
} 